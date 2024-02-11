package fr.humanbooster.fx.plages.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import fr.humanbooster.fx.plages.business.Client;
import fr.humanbooster.fx.plages.dto.ClientDto;

@Mapper(componentModel = ComponentModel.SPRING, uses = { PaysMapper.class, LienDeParenteMapper.class })
public interface ClientMapper {

	@Mapping(source = "paysDto", target = "pays")
	@Mapping(source = "lienDeParenteDto", target = "lienDeParente")
	@Mapping(target = "reservations", ignore = true)
	@Mapping(target = "dateHeureInscription", ignore = true)
	Client toEntity(ClientDto clientDto);

	@Mapping(source = "pays", target = "paysDto")
	@Mapping(source = "lienDeParente", target = "lienDeParenteDto")
	ClientDto toDto(Client client);

}