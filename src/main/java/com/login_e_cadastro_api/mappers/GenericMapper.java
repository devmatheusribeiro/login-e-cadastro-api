package com.login_e_cadastro_api.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.stream.Collectors;

public class GenericMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Converte um objeto de origem para um objeto do tipo especificado.
     *
     * @param <S> O tipo do objeto de origem.
     * @param <T> O tipo do objeto de destino.
     * @param source O objeto de origem a ser mapeado.
     * @param targetClass A classe do tipo de destino.
     * @return Um novo objeto do tipo especificado, com os dados mapeados do objeto de origem.
     */
    public static <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    /**
     * Converte uma lista de objetos de origem para uma lista de objetos do tipo especificado.
     *
     * @param <S> O tipo dos objetos de origem.
     * @param <T> O tipo dos objetos de destino.
     * @param sourceList A lista de objetos de origem a ser mapeada.
     * @param targetClass A classe do tipo de destino.
     * @return Uma nova lista de objetos do tipo especificado, com os dados mapeados dos objetos de origem.
     */
    public static <S, T> List<T> mapList(List<S> sourceList, Class<T> targetClass) {
        return sourceList.stream()
                .map(source -> map(source, targetClass))
                .collect(Collectors.toList());
    }

    /**
     * Mapeia os dados de um objeto de origem para um objeto de destino já existente.
     * Este método é útil para atualizar um objeto sem criar uma nova instância.
     *
     * @param <S> O tipo do objeto de origem.
     * @param <D> O tipo do objeto de destino.
     * @param source O objeto de origem com os dados a serem mapeados.
     * @param destination O objeto de destino que será atualizado com os novos dados.
     * @return O objeto de destino atualizado com os dados do objeto de origem.
     */
    public static <S, D> D map(final S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }
}

