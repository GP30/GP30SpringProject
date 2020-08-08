package softuni.springproject.services.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.springproject.data.models.Chef;
import softuni.springproject.data.repositories.ChefsRepository;
import softuni.springproject.errors.ChefNotFoundException;
import softuni.springproject.services.factories.ChefsFactory;
import softuni.springproject.services.models.ChefCreateServiceModel;
import softuni.springproject.services.models.ChefDetailsServiceModel;
import softuni.springproject.services.services.ChefsService;

import java.util.Optional;

@Service
public class ChefsServiceImpl implements ChefsService {
    private final ChefsRepository chefsRepository;
    private final ChefsFactory chefsFactory;
    private final ModelMapper mapper;

    public ChefsServiceImpl(
            ChefsRepository chefsRepository,
            ChefsFactory chefsFactory,
            ModelMapper mapper) {
        this.chefsRepository = chefsRepository;
        this.chefsFactory = chefsFactory;
        this.mapper = mapper;
    }

    @Override
    public ChefDetailsServiceModel getByName(String name) {
        Chef chef = chefsRepository.getByNameIgnoreCase(name).orElseThrow(() ->
                new ChefNotFoundException("There is no such chef!"));

        ChefDetailsServiceModel serviceModel = mapper.map(chef, ChefDetailsServiceModel.class);

        return serviceModel;
    }

    @Override
    public Chef create(ChefCreateServiceModel serviceModel) {
        Chef chef = chefsFactory.create(serviceModel.getName(), serviceModel.getGender());
        chefsRepository.save(chef);
        return chef;
    }


}
