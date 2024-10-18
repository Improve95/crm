package ru.improve.crm.unit.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import ru.improve.crm.dao.repositories.SellerRepository;
import ru.improve.crm.dto.seller.SellerDataResponse;
import ru.improve.crm.dto.seller.SellerPatchRequest;
import ru.improve.crm.dto.seller.SellerPostRequest;
import ru.improve.crm.dto.seller.SellerPostResponse;
import ru.improve.crm.error.exceptions.AlreadyExistException;
import ru.improve.crm.error.exceptions.NotFoundException;
import ru.improve.crm.mappers.SellerMapper;
import ru.improve.crm.models.Seller;
import ru.improve.crm.services.imp.SellerServiceImp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SellerServiceTest {

    @Mock
    SellerRepository sellerRepository;

    @Mock
    SellerMapper sellerMapper;

    @InjectMocks
    SellerServiceImp sellerService;

    List<SellerPostRequest> sellerPostRequests;
    List<Seller> sellers;
    List<SellerPostResponse> sellerPostResponses;
    List<SellerDataResponse> sellerDataResponses;

    LocalDateTime dateTime = LocalDateTime.now();

    @BeforeEach
    void initialLists() {
        sellerPostRequests = List.of(
                new SellerPostRequest("name1", "contact1"),
                new SellerPostRequest("name", "contact2")
        );
        sellers = List.of(
                new Seller(1, "name1", "contact1", dateTime),
                new Seller(2, "name2", "contact2", dateTime)
        );
        sellerPostResponses = List.of(
                new SellerPostResponse(1, dateTime),
                new SellerPostResponse(2, dateTime)
        );
        sellerDataResponses = List.of(
                new SellerDataResponse(1, "name1", "contact1", dateTime),
                new SellerDataResponse(2, "name2", "contact2", dateTime)
        );
    }

    @Test
    void getAllSellers_ReturnsAllSellers() {
        //given
        doReturn(sellers).when(this.sellerRepository).findAll();
        doReturn(sellerDataResponses.get(0)).when(this.sellerMapper).toSellerDataResponse(sellers.get(0));
        doReturn(sellerDataResponses.get(1)).when(this.sellerMapper).toSellerDataResponse(sellers.get(1));

        //when
        var sdrsRet = sellerService.getAllSellers();

        //when
        assertNotNull(sdrsRet);
        assertEquals(sellerDataResponses, sdrsRet);
        verify(sellerRepository).findAll();
    }

    @Test
    void getSellerById_SellerIsExist_ReturnsAllSellers() {
        //given
        doReturn(Optional.ofNullable(sellers.get(0))).when(this.sellerRepository).findById(1);
        doReturn(sellerDataResponses.get(0)).when(this.sellerMapper).toSellerDataResponse(sellers.get(0));

        //when
        var sdrsRet = sellerService.getSellerById(1);

        //when
        assertNotNull(sdrsRet);
        assertEquals(sellerDataResponses.get(0), sdrsRet);
        verify(sellerRepository).findById(1);
    }

    @Test
    void getSellerById_SellerNotExist_ReturnsAllSellers() {
        //given
        doReturn(Optional.ofNullable(null)).when(this.sellerRepository).findById(1);

        //when
        NotFoundException ex = assertThrows(NotFoundException.class, () ->
                sellerService.getSellerById(1));

        //when
        assertNotNull(ex);
        assertEquals("not found seller", ex.getMessage());
        verify(sellerRepository).findById(1);
    }

    @Test
    void saveSeller_SellerIsUnique_ReturnsPostResponse() {
        //given
        doReturn(sellers.get(0)).when(this.sellerMapper).toSeller(sellerPostRequests.get(0));
        doReturn(sellers.get(0)).when(this.sellerRepository).save(sellers.get(0));
        doReturn(sellerPostResponses.get(0)).when(this.sellerMapper).toSellerPostResponse(sellers.get(0));

        //when
        SellerPostResponse spr = sellerService.saveSeller(sellerPostRequests.get(0));

        //then
        assertNotNull(spr);
        assertEquals(sellerPostResponses.get(0), spr);
        verify(sellerRepository).save(sellers.get(0));
        verify(sellerMapper).toSellerPostResponse(sellers.get(0));
    }

    @Test
    void saveSeller_SellerNotUnique_ReturnsPostResponse() {
        //given
        doReturn(sellers.get(0)).when(this.sellerMapper).toSeller(sellerPostRequests.get(0));
        doThrow(new DataIntegrityViolationException("")).when(this.sellerRepository).save(sellers.get(0));

        //when
        AlreadyExistException ex = assertThrows(AlreadyExistException.class, () ->
                sellerService.saveSeller(sellerPostRequests.get(0)));

        //then
        assertNotNull(ex);
        assertEquals(List.of("contactInfo"), ex.getFieldsWithErrorList());
        verify(sellerRepository).save(sellers.get(0));
        verify(sellerMapper).toSeller(sellerPostRequests.get(0));
    }

    @Test
    void patchSeller_PatchDataIsValid_ReturnsPatchedSellerDate() {
        //given
        SellerPatchRequest sellerPatchRequest = new SellerPatchRequest("name10", "contact10");
        doReturn(Optional.ofNullable(sellers.get(0))).when(this.sellerRepository).findById(1);
        doNothing().when(this.sellerMapper).patchSeller(sellerPatchRequest, sellers.get(0));
        doReturn(sellerDataResponses.get(0)).when(this.sellerMapper).toSellerDataResponse(sellers.get(0));

        //when
        SellerDataResponse sdrsRet = sellerService.patchSeller(1, sellerPatchRequest);

        //then
        assertNotNull(sdrsRet);
        assertEquals(sellerDataResponses.get(0), sdrsRet);
        verify(sellerRepository).findById(1);
        verify(sellerMapper).patchSeller(sellerPatchRequest, sellers.get(0));
    }

    @Test
    void deleteSeller_SellerExist() {
        //given
        doReturn(true).when(this.sellerRepository).existsById(1);

        //when
        sellerService.deleteSellerById(1);

        //then
        verify(sellerRepository).existsById(1);
        verify(sellerRepository).deleteById(1);
    }

    @Test
    void deleteSeller_SellerNotExist() {
        //given
        doReturn(false).when(this.sellerRepository).existsById(1);

        //when
        NotFoundException ex = assertThrows(NotFoundException.class, () ->
                sellerService.deleteSellerById(1));

        //then
        assertNotNull(ex);
        assertEquals("not found seller for delete", ex.getMessage());
        assertEquals(List.of("id"), ex.getFieldsWithErrorList());
        verify(sellerRepository).existsById(1);
    }
}
