package br.com.marco.cadeacerva.store.endpoint;

import br.com.marco.cadeacerva.store.domain.Store;
import br.com.marco.cadeacerva.store.endpoint.dto.StoreDTO;
import br.com.marco.cadeacerva.store.domain.StoreRepository;
import br.com.marco.cadeacerva.store.endpoint.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreRepository storeRepository;

    @GetMapping
    public Page<StoreDTO> listAll(Pageable pageable) {
        return storeRepository.findAll(pageable).map(StoreDTO::new);
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
        Store store = storeRepository.findById(id).orElseThrow(NotFoundException::new);
        storeRepository.delete(store);
    }

    @PutMapping("/{id}")
    public StoreDTO update(@PathVariable("id") String id, @RequestBody StoreDTO storeDto) {
        Store changedStore = storeRepository.findById(id).map(s -> s.setName(storeDto.getName())).orElseThrow(NotFoundException::new);
        return new StoreDTO(storeRepository.save(changedStore));
    }
}
