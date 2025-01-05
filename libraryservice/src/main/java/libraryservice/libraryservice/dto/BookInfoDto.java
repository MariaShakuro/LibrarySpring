package libraryservice.libraryservice.dto;


import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name="book_info")
public class BookInfoDto {
    private Long bookId;
    private String status;
    private LocalDateTime borrowTime;
    private LocalDateTime returnTime;
   private Boolean isDeleted=false;
   public BookInfoDto(Long bookId, String status, LocalDateTime borrowTime,
                       LocalDateTime returnTime, Boolean isDeleted){
        this.bookId=bookId;
        this.status=status;
        this.borrowTime=borrowTime !=null ? borrowTime : LocalDateTime.now();
        this.returnTime=returnTime !=null ? returnTime : LocalDateTime.now().plusWeeks(2);
        this.isDeleted=isDeleted;
    }
    public BookInfoDto(){}

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBorrowTime(LocalDateTime borrowTime) {
        this.borrowTime = borrowTime;
    }

    public void setReturnTime(LocalDateTime returnTime) {
        this.returnTime = returnTime;
    }

    public Long getBookId() {
        return bookId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getBorrowTime() {
        return borrowTime;
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}

