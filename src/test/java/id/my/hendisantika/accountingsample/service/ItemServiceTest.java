package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.item.ItemRequest;
import id.my.hendisantika.accountingsample.dto.item.ItemResponse;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.model.Item;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.model.enums.ItemType;
import id.my.hendisantika.accountingsample.repository.AccountRepository;
import id.my.hendisantika.accountingsample.repository.ItemRepository;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import id.my.hendisantika.accountingsample.repository.TaxRepository;
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
@DisplayName("ItemService Tests")
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;
    @Mock
    private OrganizationRepository organizationRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TaxRepository taxRepository;

    @InjectMocks
    private ItemService itemService;

    private MockedStatic<SecurityUtils> securityUtilsMock;
    private Organization organization;
    private Item item;
    private ItemRequest itemRequest;

    @BeforeEach
    void setUp() {
        securityUtilsMock = mockStatic(SecurityUtils.class);
        securityUtilsMock.when(SecurityUtils::getCurrentOrganizationId).thenReturn(1L);

        organization = Organization.builder()
                .name("Test Org")
                .build();
        organization.setId(1L);

        item = Item.builder()
                .organization(organization)
                .name("Test Item")
                .code("ITEM001")
                .itemType(ItemType.PRODUCT)
                .salePrice(BigDecimal.valueOf(100))
                .isActive(true)
                .build();
        item.setId(1L);

        itemRequest = new ItemRequest();
        itemRequest.setName("New Item");
        itemRequest.setCode("ITEM002");
        itemRequest.setItemType(ItemType.PRODUCT);
        itemRequest.setSalePrice(BigDecimal.valueOf(150));
        itemRequest.setIsActive(true);
    }

    @AfterEach
    void tearDown() {
        securityUtilsMock.close();
    }

    @Test
    @DisplayName("Should get all items")
    void getAllItems_Success() {
        when(itemRepository.findByOrganizationId(1L)).thenReturn(Collections.singletonList(item));
        List<ItemResponse> responses = itemService.getAllItems();
        assertThat(responses).hasSize(1);
        verify(itemRepository).findByOrganizationId(1L);
    }

    @Test
    @DisplayName("Should create item successfully")
    void createItem_Success() {
        when(itemRepository.existsByCodeAndOrganizationId(any(), any())).thenReturn(false);
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemResponse response = itemService.createItem(itemRequest);
        assertThat(response).isNotNull();
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    @DisplayName("Should throw exception when item code already exists")
    void createItem_DuplicateCode_ThrowsException() {
        when(itemRepository.existsByCodeAndOrganizationId(any(), any())).thenReturn(true);

        assertThatThrownBy(() -> itemService.createItem(itemRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Item with this code already exists");
    }

    @Test
    @DisplayName("Should get item by ID")
    void getItemById_Success() {
        when(itemRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(item));
        ItemResponse response = itemService.getItemById(1L);
        assertThat(response).isNotNull();
        assertThat(response.getCode()).isEqualTo("ITEM001");
    }

    @Test
    @DisplayName("Should delete item")
    void deleteItem_Success() {
        when(itemRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(item));
        itemService.deleteItem(1L);
        verify(itemRepository).delete(item);
    }
}
