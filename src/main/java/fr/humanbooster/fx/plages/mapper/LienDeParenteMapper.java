package fr.humanbooster.fx.plages.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import fr.humanbooster.fx.plages.business.LienDeParente;
import fr.humanbooster.fx.plages.dto.LienDeParenteDto;

@Mapper(componentModel = "spring")
public interface LienDeParenteMapper {

	LienDeParenteMapper INSTANCE = Mappers.getMapper(LienDeParenteMapper.class);

	LienDeParenteDto toDto(LienDeParente lienDeParente);

	LienDeParente toEntity(LienDeParenteDto lienDeParenteDto);

}