package libraryservice.libraryservice.service;


import libraryservice.libraryservice.entity.BookInfo;
import libraryservice.libraryservice.repository.BookInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookInfoService {
    @Autowired
    private BookInfoRepository bookInfoRepository;

    public BookInfo saveBookInfo(Long idBook){
        BookInfo bookInfo=new BookInfo();
        bookInfo.setId(idBook);
        bookInfo.setBorrowTime(LocalDateTime.now());
        bookInfo.setReturnTime(LocalDateTime.now().plusWeeks(2));
        System.out.println("Saving book info:"+ bookInfo);//Log
        return bookInfoRepository.save(bookInfo);
    }
    public List<BookInfo>getAllBookInfo(){
        return bookInfoRepository.findAll();
    }
    public BookInfo updateBookInfo(Long idBook, BookInfo details){
        BookInfo bookInfo=bookInfoRepository.findById(idBook).orElseThrow(()->new RuntimeException("Book not found"));
        bookInfo.setBorrowTime(details.getBorrowTime());
        bookInfo.setReturnTime(details.getReturnTime());
        return bookInfoRepository.save(bookInfo);
    }
    public BookInfo getBookInfoById(Long idBook) {
        return bookInfoRepository.findById(idBook).orElseThrow(() -> new RuntimeException("Book not found"));
    }
}
