package softuni.springproject.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.springproject.data.models.Gender;
import org.modelmapper.Converter;
import softuni.springproject.services.models.chefs.ChefCreateServiceModel;
import softuni.springproject.web.view.models.ChefCreateModel;

@Configuration
public class ModelMapperConfig {
    private static ModelMapper mapper;

    static {
        mapper = new ModelMapper();
        initMapper(mapper);
    }

    private static void initMapper(ModelMapper mapper) {
        Converter<String, Gender> stringToGenderConverter =
                ctx -> Gender.valueOf(ctx.getSource().toUpperCase());

        mapper.createTypeMap(ChefCreateModel.class, ChefCreateServiceModel.class)
                .addMappings(map -> map
                        .using(stringToGenderConverter)
                        .map(
                                ChefCreateModel::getGender,
                                ChefCreateServiceModel::setGender
                        )
                );
    }

    @Bean
    public ModelMapper modelMapper() {
        return mapper;
    }
}
