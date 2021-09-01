package com.yocale.billmanagement.services;

import com.yocale.billmanagement.dtos.BillDto;
import com.yocale.billmanagement.dtos.BillRegisterDto;
import com.yocale.billmanagement.entities.Bill;
import com.yocale.billmanagement.entities.User;
import com.yocale.billmanagement.exceptions.InvalidIdException;
import com.yocale.billmanagement.repositories.BillRepository;
import com.yocale.billmanagement.repositories.UserRepository;
import com.yocale.billmanagement.security.model.UserPrincipal;
import com.yocale.billmanagement.utils.BillValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {
    private BillRepository repository;
    private final UserRepository userRepository;
    private ModelMapper mapper;

    @Autowired
    public BillService(BillRepository repository, UserRepository userRepository, ModelMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Transactional
    public BillDto register(BillRegisterDto billDto, UserPrincipal creator) {
        Bill bill = mapper.map(billDto, Bill.class);
        bill.setUser(findUser(creator));
        return mapper.map(repository.save(bill), BillDto.class);
    }

    private User findUser(UserPrincipal principal) {
        return userRepository.findByUsername(principal.getUsername()).get();
    }

    @Transactional
    public void delete(long id, UserPrincipal principal) throws InvalidIdException, AccessDeniedException {
        Bill bill = repository.findById(id)
                .orElseThrow(() -> new InvalidIdException("Bill with id " + id + " does not exist."));
        BillValidator.validateUser(findUser(principal), bill);
        repository.delete(bill);
    }

    public List<BillDto> getAll(UserPrincipal principal) {
        User user = findUser(principal);
        if (principal.isAdmin())
            return repository.findAll()
                    .stream()
                    .map(b -> mapper.map(b, BillDto.class))
                    .collect(Collectors.toList());
        else
            return user.getBills()
                    .stream()
                    .map(b -> mapper.map(b, BillDto.class))
                    .collect(Collectors.toList());
    }

    public BillDto update(BillRegisterDto billDto, long id, UserPrincipal principal) throws InvalidIdException, AccessDeniedException {
        Bill bill = repository.findById(id)
                .orElseThrow(() -> new InvalidIdException("Bill with id " + id + " does not exist."));
        BillValidator.validateUser(findUser(principal), bill);

        bill.setCategory(billDto.getCategory());
        bill.setPrice(billDto.getPrice());
        bill.setDate(billDto.getDate());

        return mapper.map(repository.save(bill), BillDto.class);
    }

    public BillDto findById(long id, UserPrincipal principal) throws InvalidIdException, AccessDeniedException {
        Bill bill = repository.findById(id)
                .orElseThrow(() -> new InvalidIdException("Bill with id " + id + " does not exist."));
        BillValidator.validateUser(findUser(principal), bill);

        return mapper.map(bill, BillDto.class);
    }

}
