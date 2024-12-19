package libraryservice.libraryservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.Version;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    private String status;
    private LocalDateTime borrowTime;
    private LocalDateTime returnTime;
   private Boolean isDeleted=false;
   public  void setIsDeleted(Boolean isDeleted){
       this.isDeleted=isDeleted;
   }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setId(Long id){
        this.id=id;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBorrowTime(LocalDateTime borrowTime){
        this.borrowTime=borrowTime;
    }
    public void setReturnTime(LocalDateTime returnTime){
        this.returnTime=returnTime;
    }
    public Long getId(){
        return id;
    }
    public void setStatus(String status){
        this.status=status;
    }

    public LocalDateTime getBorrowTime() {
        return borrowTime;
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }
    public String getStatus(){
        return status;
    }
}
