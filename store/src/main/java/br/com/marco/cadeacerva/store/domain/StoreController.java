package br.com.marco.cadeacerva.store.domain;

import br.com.marco.cadeacerva.store.infra.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreRepository storeRepository;

    @GetMapping
    public List<StoreDTO> listAll() {
        return storeRepository.findAll().stream().map(StoreDTO::new).collect(toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StoreDTO save(@RequestBody StoreDTO storeDto) {
        return new StoreDTO(storeRepository.save(new Store(storeDto.getName())));
    }

    @GetMapping("/{id}")
    public  StoreDTO find(@PathVariable("id") String id) {
        return storeRepository.findById(id).map(StoreDTO::new).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        storeRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public StoreDTO update(@PathVariable("id") String id, @RequestBody StoreDTO storeDto) {
        Store changedStore = storeRepository.findById(id).map(s -> s.setName(storeDto.getName())).orElseThrow(NotFoundException::new);
        return new StoreDTO(storeRepository.save(changedStore));
    }
}
