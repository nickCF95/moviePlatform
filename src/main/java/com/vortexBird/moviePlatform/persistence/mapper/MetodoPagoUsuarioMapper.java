package com.vortexBird.moviePlatform.persistence.mapper;

import com.vortexBird.moviePlatform.domain.MetodoPagoUsuario;
import com.vortexBird.moviePlatform.domain.Usuario;
import com.vortexBird.moviePlatform.persistence.entity.User;
import com.vortexBird.moviePlatform.persistence.entity.UserPaymentMethod;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",uses = {UsuarioMapper.class})
public interface MetodoPagoUsuarioMapper {
    @Mappings({
            @Mapping(source = "idUserPaymentMethod", target = "metodoPagoUsuarioId"),
            @Mapping(source = "cardNumber", target = "numeroTarjeta"),
            @Mapping(source = "expirationDate", target = "fechaExpiracion"),
            @Mapping(source = "user", target = "usuario"),
            @Mapping(source = "enable", target = "habilitado"),
    })
    MetodoPagoUsuario toMetodoPagoUsuario(UserPaymentMethod userPaymentMethod);

    List<MetodoPagoUsuario> toMetodosPagoUsuarios(List<UserPaymentMethod> userPaymentMethods);

    @InheritInverseConfiguration
    UserPaymentMethod toUserPaymentMethod(MetodoPagoUsuario metodoPagoUsuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserPaymentMethodFromMetodoPagoUsuario(MetodoPagoUsuario metodoPagoUsuario, @MappingTarget UserPaymentMethod userPaymentMethod);
}
