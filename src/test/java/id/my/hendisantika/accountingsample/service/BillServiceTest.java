package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.bill.BillItemRequest;
import id.my.hendisantika.accountingsample.dto.bill.BillRequest;
import id.my.hendisantika.accountingsample.dto.bill.BillResponse;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.model.Bill;
import id.my.hendisantika.accountingsample.model.Item;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.model.Vendor;
import id.my.hendisantika.accountingsample.model.enums.BillStatus;
import id.my.hendisantika.accountingsample.repository.BillRepository;
import id.my.hendisantika.accountingsample.repository.ItemRepository;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import id.my.hendisantika.accountingsample.repository.VendorRepository;
import id.my.hendisantika.accountingsample.util.SecurityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("BillService Tests")
class BillServiceTest {

    @Mock
    private BillRepository billRepository;
    @Mock
    private VendorRepository vendorRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private BillService billService;

    private MockedStatic<SecurityUtils> securityUtilsMock;
    private Organization organization;
    private Vendor vendor;
    private Item item;
    private Bill bill;
    private BillRequest billRequest;

    @BeforeEach
    void setUp() {
        securityUtilsMock = mockStatic(SecurityUtils.class);
        securityUtilsMock.when(SecurityUtils::getCurrentOrganizationId).thenReturn(1L);

        organization = Organization.builder()
                .name("Test Org")
                .build();
        organization.setId(1L);

        vendor = Vendor.builder()
                .name("Test Vendor")
                .organization(organization)
                .build();
        vendor.setId(1L);

        item = Item.builder()
                .name("Test Item")
                .organization(organization)
                .build();
        item.setId(1L);

        bill = Bill.builder()
                .organization(organization)
                .vendor(vendor)
                .billNumber("BILL-001")
                .billDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(30))
                .status(BillStatus.DRAFT)
                .build();
        bill.setId(1L);

        billRequest = new BillRequest();
        billRequest.setVendorId(1L);
        billRequest.setBillNumber("BILL-002");
        billRequest.setBillDate(LocalDate.now());
        billRequest.setDueDate(LocalDate.now().plusDays(30));

        BillItemRequest itemRequest = new BillItemRequest();
        itemRequest.setItemId(1L);
        itemRequest.setQuantity(BigDecimal.valueOf(2));
        itemRequest.setUnitPrice(BigDecimal.valueOf(50));
        itemRequest.setLineOrder(1);
        billRequest.setItems(List.of(itemRequest));
    }

    @AfterEach
    void tearDown() {
        securityUtilsMock.close();
    }

    @Test
    @DisplayName("Should create bill successfully")
    void createBill_Success() {
        when(billRepository.existsByBillNumberAndOrganizationId(any(), any())).thenReturn(false);
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(vendorRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(vendor));
        when(itemRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(item));
        when(billRepository.save(any(Bill.class))).thenReturn(bill);

        BillResponse response = billService.createBill(billRequest);
        assertThat(response).isNotNull();
        verify(billRepository).save(any(Bill.class));
    }

    @Test
    @DisplayName("Should throw exception when bill number already exists")
    void createBill_DuplicateNumber_ThrowsException() {
        when(billRepository.existsByBillNumberAndOrganizationId(any(), any())).thenReturn(true);

        assertThatThrownBy(() -> billService.createBill(billRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Bill with this number already exists");
    }

    @Test
    @DisplayName("Should throw exception when due date before bill date")
    void createBill_InvalidDueDate_ThrowsException() {
        billRequest.setDueDate(LocalDate.now().minusDays(1));
        when(billRepository.existsByBillNumberAndOrganizationId(any(), any())).thenReturn(false);
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(vendorRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(vendor));

        assertThatThrownBy(() -> billService.createBill(billRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Due date cannot be before bill date");
    }

    @Test
    @DisplayName("Should get all bills")
    void getAllBills_Success() {
        when(billRepository.findByOrganizationId(1L)).thenReturn(Collections.singletonList(bill));
        List<BillResponse> responses = billService.getAllBills();
        assertThat(responses).hasSize(1);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-draft bill")
    void deleteBill_NonDraft_ThrowsException() {
        bill.setStatus(BillStatus.PAID);
        when(billRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(bill));

        assertThatThrownBy(() -> billService.deleteBill(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Only draft bills can be deleted");
    }
}
