package softuni.springproject.services.services.implementations;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.springproject.data.models.Chef;
import softuni.springproject.data.repositories.ChefsRepository;
import softuni.springproject.errors.ChefNotFoundException;
import softuni.springproject.services.factories.ChefsFactory;
import softuni.springproject.services.models.chefs.ChefCreateServiceModel;
import softuni.springproject.services.models.chefs.ChefDetailsServiceModel;
import softuni.springproject.services.services.ChefsService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChefsServiceImpl implements ChefsService {
    private final ChefsRepository chefsRepository;
    private final ChefsFactory chefsFactory;
    private final ModelMapper mapper;

    @Override
    public ChefDetailsServiceModel getByName(String name) {
        Optional<Chef> chefResult = chefsRepository.getByNameIgnoreCase(name);
        if(chefResult.isEmpty()) {
            throw new ChefNotFoundException("Chef with such a name does not exist");
        }

        Chef chef = chefResult.get();

        ChefDetailsServiceModel serviceModel = mapper.map(chef, ChefDetailsServiceModel.class);

        return serviceModel;
    }

    @Override
    public Chef create(ChefCreateServiceModel serviceModel) {
        Chef chef = chefsFactory.create(serviceModel.getName(), serviceModel.getGender());
        chefsRepository.save(chef);
        return chef;
    }

    @Override
    public List<ChefDetailsServiceModel> getAllChefs() {
        return chefsRepository.findAll()
                .stream()
                .map(chef -> mapper.map(chef, ChefDetailsServiceModel.class))
                .collect(Collectors.toList());
    }


}
