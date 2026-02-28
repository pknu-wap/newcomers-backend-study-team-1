package com.bumboo.shop.Item;

import com.bumboo.shop.comment.Comment;
import com.bumboo.shop.comment.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(@ModelAttribute Item item,String username){
        item.setUsername(username);
        itemRepository.save(item);
    }
    public void updateItem(Item item, Long id){
        Item pre = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 아이템이 없습니다."));
        pre.setPrice(item.getPrice());
        pre.setTitle(item.getTitle());
        pre.setImage(item.getImage());
    }
    public void deleteItem(Long docid){
        Item item = itemRepository.findById(docid).orElseThrow(() -> new RuntimeException("해당 아이템이 없습니다."));
        itemRepository.delete(item);
    }
}
