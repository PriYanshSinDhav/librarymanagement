package com.motada.librarymanagement.service;


import com.motada.librarymanagement.entity.Book;
import com.motada.librarymanagement.model.INPage;
import com.motada.librarymanagement.model.SearchRequest;
import com.motada.librarymanagement.model.exceptions.CreateException;
import com.motada.librarymanagement.model.exceptions.DeleteException;
import com.motada.librarymanagement.model.exceptions.SearchException;
import com.motada.librarymanagement.model.exceptions.UpdateException;
import com.motada.librarymanagement.model.request.BookCreateRequest;
import com.motada.librarymanagement.model.response.*;
import com.motada.librarymanagement.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BookService extends BaseService {



    private final BookRepository bookRepository;


    @Transactional(rollbackFor = CreateException.class)
    public GenericResponse<String> addBook(BookCreateRequest bookCreateRequest) throws CreateException {

        try{
            Book book = new Book();
            book.setAuthor(bookCreateRequest.getAuthor());
            book.setGenre(bookCreateRequest.getGenre());
            book.setTitle(bookCreateRequest.getTitle());
            book.setAvailability(true);
            book.setPublishedYear(bookCreateRequest.getPublishedYear());
            book.setDeleted(false);
            book.setCreatedOn(currentTimestamp());
            book.setUpdatedOn(currentTimestamp());
            book = bookRepository.save(book);
            return new GenericResponse<>(0L,"Success",String.valueOf(book.getId()));
        }catch (Exception e){
            logError(e);
            throw new CreateException("Error creating book record");
        }

    }


    @Transactional(readOnly = true)
    public GenericResponse<BookListResponse> getAllBooks(SearchRequest searchRequest) {

        Pageable pageable = PageRequest.of(searchRequest.getPageNumber(), searchRequest.getPageSize(), parseToValidSort(searchRequest.getSortColumn(), searchRequest.getSortDirection()));

        String title = "";
        if(searchRequest.getSearchFilter()!=null){
           title = searchRequest.getSearchFilter();;
        }

        Page<BookListItem> bookListItemPage = bookRepository.findByTitle(title,pageable);

        INPage page = new INPage(bookListItemPage.getSize(), bookListItemPage.getTotalElements(), bookListItemPage.getTotalPages(),
                bookListItemPage.getNumber());

        BookListResponse bookListResponse = new BookListResponse();
        bookListResponse.setBookItemList(bookListItemPage.getContent());
        bookListResponse.setPage(page);

       return new GenericResponse<>(0L , "Sucess" , bookListResponse);
    }



    @Transactional(readOnly = true)
    public GenericResponse<BookViewResponse> getBookById(Long id) throws SearchException {
        Book book =  bookRepository.findById(id)
                .orElseThrow(() -> new SearchException("Book not found with ID: " + id));

        try{
            BookViewResponse bookViewResponse = new BookViewResponse();
            bookViewResponse.setAuthor(book.getAuthor());
            bookViewResponse.setGenre(book.getGenre());
            bookViewResponse.setPublishedYear(book.getPublishedYear());
            bookViewResponse.setTitle(book.getTitle());
            bookViewResponse.setAvailable(book.isAvailability() ? "Yes" : "No");

            return new GenericResponse<>(0L,"Success",bookViewResponse);
        }catch (Exception e){
            logError(e);
            return new GenericResponse<>(-1L,"Error fetching book response reason :" + e.getMessage());
        }

    }



    @Transactional(rollbackFor = UpdateException.class)
    public GenericResponse<String> updateBook(Long id, BookCreateRequest bookUpdateRequest) throws SearchException, UpdateException {
        if (bookRepository.findById(id).isEmpty()) {
            throw new SearchException("Cannot update: Book not found with ID: " + id);
        }
        try{
            Book book = bookRepository.findById(id).get();

            book.setPublishedYear(bookUpdateRequest.getPublishedYear());
            book.setAuthor(bookUpdateRequest.getAuthor());
            book.setGenre(bookUpdateRequest.getGenre());
            book.setTitle(bookUpdateRequest.getTitle());
            book = bookRepository.save(book);
            return new GenericResponse<>(0L,"Success",String.valueOf(book.getId()));
        }catch (Exception e){
            logError(e);
            throw new UpdateException("Error updating book record");
        }

    }


    @Transactional(rollbackFor = DeleteException.class)
    public GenericResponse<String> deleteBook(Long id) throws SearchException, DeleteException {
        if (bookRepository.findById(id).isEmpty()) {
            throw new SearchException("Cannot delete: Book not found with ID: " + id);
        }
        try{
            Book book = bookRepository.findById(id).get();

            book.setDeleted(true);
            book.setUpdatedOn(currentTimestamp());
            book = bookRepository.save(book);
            return new GenericResponse<>(0L,"Success",String.valueOf(book.getId()));
        }catch (Exception e){
            logError(e);
            throw new DeleteException("Error deleting book record");
        }
    }


    protected Sort parseToValidSort(String columnName, String direction) {

        String sortColum;

        if (columnName != null) {
            switch (columnName) {
                case "title":
                    sortColum = columnName;
                    break;
                case "genre":
                    sortColum = columnName;
                    break;
                case "author":
                    sortColum = columnName;
                    break;
                case "publishedYear":
                    sortColum = columnName;
                    break;
                default:
                    sortColum = "publishedYear";
            }
        } else {
            sortColum = "publishedYear";
        }

        if (direction != null && direction.equals("asc")) {
            return Sort.by(sortColum).ascending();
        } else {
            return Sort.by(sortColum).descending();
        }

    }


}
