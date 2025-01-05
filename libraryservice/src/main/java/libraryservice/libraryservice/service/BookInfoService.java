package libraryservice.libraryservice.service;


import libraryservice.libraryservice.client.JwtAuthClient;
import libraryservice.libraryservice.dto.BookInfoDto;
import libraryservice.libraryservice.entity.BookInfo;
import libraryservice.libraryservice.repository.BookInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookInfoService {
    private static final Logger logger= LoggerFactory.getLogger(BookInfoService.class);
    @Autowired
    private BookInfoRepository bookInfoRepository;
    @Autowired
    private JwtAuthClient jwtAuthClient;



    public List<BookInfoDto> getAvailableBooks(String jwtToken) {
        String bearerToken = "Bearer " + jwtToken.trim();
        Boolean isValidToken = jwtAuthClient.validateToken(bearerToken);
        if (isValidToken) {
            List<BookInfo> availableBooks = bookInfoRepository.findByStatus("available");
            logger.info("Found " + availableBooks.size() + " available books");
            List<BookInfoDto>availableBookDTOs=availableBooks.stream()
                    .map(book -> new BookInfoDto(
                            book.getBookId(),
                            book.getStatus(),
                            book.getBorrowTime() !=null ? book.getBorrowTime() : LocalDateTime.now(),
                            book.getReturnTime() !=null ? book.getReturnTime() : LocalDateTime.now().plusWeeks(2),
                            book.getIsDeleted()))
                    .collect(Collectors.toList());
            return availableBookDTOs;
        }else{
            throw new UnauthorizedException("Invalid JWT token");
        }
    }

    public void updateBookStatus(String jwtToken, BookInfoDto bookInfoDTO){
        String bearerToken = "Bearer " + jwtToken.trim();
        Boolean isValidToken = jwtAuthClient.validateToken(bearerToken);
        if (isValidToken) {
            if(!bookInfoDTO.getStatus().equals("available") && !bookInfoDTO.getStatus().equals("taken")) {
                throw new IllegalArgumentException("Irrelevant status value");
            }
                BookInfo bookInfo = bookInfoRepository.findByBookId(bookInfoDTO.getBookId())
                        .orElseThrow(() -> new RuntimeException("Book not found"));
                bookInfo.setStatus(bookInfoDTO.getStatus());
                bookInfoRepository.save(bookInfo);

        }else{
            throw new UnauthorizedException("Invalid JWT token");
        }
    }

    public List<BookInfo>getAllBooksInfo(String jwtToken){
        String bearerToken = "Bearer " + jwtToken.trim();
        Boolean isValidToken = jwtAuthClient.validateToken(bearerToken);
        if (isValidToken) {
            List<BookInfo> allBooks=bookInfoRepository.findAll();
            allBooks.forEach(book->{
                if (book.getBorrowTime() == null)  book.setBorrowTime(LocalDateTime.now());
                if (book.getReturnTime() == null)  book.setReturnTime(LocalDateTime.now().plusWeeks(2));
            });
            return allBooks;
        }else{
            throw new UnauthorizedException("Invalid JWT token");
        }
    }
    public BookInfo updateBookInfo(Long id, BookInfo details){
        BookInfo bookInfo=bookInfoRepository.findById(id).orElseThrow(()->new RuntimeException("Book not found"));
        bookInfo.setBorrowTime(details.getBorrowTime());
        bookInfo.setReturnTime(details.getReturnTime());
        bookInfo.setStatus(details.getStatus());
        return bookInfoRepository.save(bookInfo);
    }
   /* public BookInfo saveBookInfo(Long id){
        BookInfo bookInfo=new BookInfo();
        bookInfo.setId(id);
        bookInfo.setStatus("available");
        bookInfo.setBorrowTime(LocalDateTime.now());
       bookInfo.setReturnTime(LocalDateTime.now().plusWeeks(2));
        return bookInfoRepository.save(bookInfo);
    }*/

}
