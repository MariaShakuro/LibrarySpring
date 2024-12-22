package libraryservice.libraryservice.dto;


import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


public class BookInfoDTO {
    private Long bookId;
    private String status;
    private LocalDateTime borrowTime;
    private LocalDateTime returnTime;
   private Boolean isDeleted=false;
    public BookInfoDTO (Long bookId,String status,LocalDateTime borrowTime,
                        LocalDateTime returnTime,Boolean isDeleted){
        this.bookId=bookId;
        this.status=status;
        this.borrowTime=borrowTime !=null ? borrowTime : LocalDateTime.now();
        this.returnTime=returnTime !=null ? returnTime : LocalDateTime.now().plusWeeks(2);
        this.isDeleted=isDeleted;
    }
    public BookInfoDTO(){}

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

