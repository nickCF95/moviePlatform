package com.vortexBird.moviePlatform.persistence;

import com.vortexBird.moviePlatform.domain.Usuario;
import com.vortexBird.moviePlatform.domain.repository.UsuarioRepository;
import com.vortexBird.moviePlatform.persistence.crud.UserCrudRepository;
import com.vortexBird.moviePlatform.persistence.entity.Role;
import com.vortexBird.moviePlatform.persistence.entity.User;
import com.vortexBird.moviePlatform.persistence.entity.UserDetails;
import com.vortexBird.moviePlatform.persistence.mapper.UsuarioMapper;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements UsuarioRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    UserCrudRepository userCrudRepository;

    @Autowired
    UsuarioMapper usuarioMapper;

    @Override
    public Optional<List<Usuario>> obtenerTodos() {
        return Optional.of(usuarioMapper.toUsuarios((List<User>) userCrudRepository.findAll()));
    }

    @Override
    public Optional<List<Usuario>> obtenerPorRol(int rolId) {
        return userCrudRepository.findByRoles_IdRole(rolId).map(users -> usuarioMapper.toUsuarios(users));
    }

    @Override
    public Optional<List<Usuario>> obtenerPorCoincidenciaAlias(String matchStr) {
        return userCrudRepository.searchByNicknameLike(matchStr).map(users -> usuarioMapper.toUsuarios(users));
    }

    @Override
    public Optional<List<Usuario>> obtenerPorCoincidenciaCorreo(String matchStr) {
        return userCrudRepository.searchByEmailLike(matchStr).map(users -> usuarioMapper.toUsuarios(users));
    }

    @Override
    public Optional<List<Usuario>> obtenerPorCualquierCoincidencia(String keyword) {
            String pattern = "%" + keyword.toLowerCase() + "%";

            Specification<User> spec = (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();

                // Search in User fields: nickname and email.
                predicates.add(cb.like(cb.lower(root.get("nickname")), pattern));
                predicates.add(cb.like(cb.lower(root.get("email")), pattern));

                Join<User, Role> roleJoin = root.join("roles", JoinType.LEFT);
                predicates.add(cb.like(cb.lower(roleJoin.get("name")), pattern));

                Subquery<UserDetails> detailsSubquery = query.subquery(UserDetails.class);
                Root<UserDetails> detailsRoot = detailsSubquery.from(UserDetails.class);

                Predicate joinCondition = cb.equal(detailsRoot.get("nickname"), root.get("nickname"));

                Predicate fullNamePredicate = cb.like(cb.lower(detailsRoot.get("fullName")), pattern);
                Predicate phonePredicate = cb.like(cb.lower(detailsRoot.get("phoneNumber")), pattern);
                Predicate dateOfBirthPredicate = cb.like(
                        cb.function("DATE_FORMAT", String.class, detailsRoot.get("dateOfBirth"), cb.literal("%Y-%m-%d")),
                        pattern
                );
                Predicate detailsCombined = cb.or(fullNamePredicate, phonePredicate, dateOfBirthPredicate);
                detailsSubquery.select(detailsRoot)
                        .where(cb.and(joinCondition, detailsCombined));
                predicates.add(cb.exists(detailsSubquery));
                return cb.or(predicates.toArray(new Predicate[0]));
            };
            return Optional.of(usuarioMapper.toUsuarios(userCrudRepository.findAll(spec)));
    }

    @Override
    public Optional<Usuario> obtenerPorAlias(String alias) {
        return userCrudRepository.findById(alias).map(user -> usuarioMapper.toUsuario(user));
    }

    @Override
    public Optional<Usuario> obtenerPorCorreo(String correo) {
        return userCrudRepository.findByEmail(correo).map(user -> usuarioMapper.toUsuario(user));
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioMapper.toUsuario(userCrudRepository.save(usuarioMapper.toUser(usuario)));
    }

    @Override
    public void update(Usuario usuario) {
        User user = userCrudRepository.findById(usuario.getAlias()).orElseThrow(NoResultException::new);
        LOGGER.info("User find it: " + user);
        LOGGER.info("Usuario: " + usuario);
        usuarioMapper.updateUserFromUsuario(usuario, user);
        LOGGER.info("Post user updated: " + user);
        userCrudRepository.save(user);
    }

    @Override
    public void delete(String alias) {
        userCrudRepository.deleteById(alias);
    }
}
