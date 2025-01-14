package libraryservice.libraryservice.dto;

import libraryservice.libraryservice.entity.BookInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookInfoMapper {
    BookInfoMapper INSTANCE = Mappers.getMapper(BookInfoMapper.class);

    BookInfoDto toDto(BookInfo bookInfo);

    BookInfo toEntity(BookInfoDto bookInfoDto);
}