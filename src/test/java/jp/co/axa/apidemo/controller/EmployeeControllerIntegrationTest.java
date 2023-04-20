package jp.co.axa.apidemo.controller;

import static jp.co.axa.apidemo.constant.Path.EMPLOYEES_BASE_PATH;
import static jp.co.axa.apidemo.constant.Path.PATH_SEPARATOR;

import java.util.List;
import java.util.Map;
import jp.co.axa.apidemo.ApiDemoApplication;
import jp.co.axa.apidemo.constant.ApiConstants;
import jp.co.axa.apidemo.constant.Path;
import jp.co.axa.apidemo.dto.EmployeeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;

/**
 * Spring boot integration test
 */
@SpringBootTest(classes = ApiDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerIntegrationTest {

    /**
     * base url path (api/v1/employees)
     */
    private static final String BASE_PATH_URL = Path.API_PATH + EMPLOYEES_BASE_PATH;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void crudOperation_success() {
        // url with id  (api/v1/employees/100)
        String basePathUrlWithId = BASE_PATH_URL + PATH_SEPARATOR + "100";

        // ******************** 1.CREATE OPERATION ******************** \\
        EmployeeDTO createDto = EmployeeDTO.builder()
                .id(100L)
                .department("IT Department")
                .name("Vennila")
                .salary(100000)
                .build();
        // create resource with id 100
        ResponseEntity<EmployeeDTO> createResponseEntity = sendRequest(BASE_PATH_URL, HttpMethod.POST, createDto);
        // create success, returns 201 status code
        Assertions.assertEquals(HttpStatus.CREATED.value(), createResponseEntity.getStatusCodeValue());

        // ******************** 2.READ OPERATION ******************** \\
        // read the created resource for id 100
        ResponseEntity<EmployeeDTO> readResponseEntity = sendRequest(basePathUrlWithId, HttpMethod.GET, null);
        // read success, returns 200 status code
        Assertions.assertEquals(HttpStatus.OK.value(), readResponseEntity.getStatusCodeValue());
        Assertions.assertEquals(createDto, readResponseEntity.getBody());

        // ******************** 3.UPDATE OPERATION ******************** \\
        EmployeeDTO updateDto = EmployeeDTO.builder()
                .id(100L)
                .department("Sales Department")
                .name("Gowri")
                .salary(10000000)
                .build();
        ResponseEntity<EmployeeDTO> updateResponse = sendRequest(basePathUrlWithId, HttpMethod.PUT, updateDto);
        // update success
        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), updateResponse.getStatusCodeValue());
        // read again to check whether the resource is updated
        ResponseEntity<EmployeeDTO> updateReadResponse = sendRequest(basePathUrlWithId, HttpMethod.GET, null);
        Assertions.assertEquals(HttpStatus.OK.value(), readResponseEntity.getStatusCodeValue());
        Assertions.assertEquals(updateDto, updateReadResponse.getBody());

        // ******************** 4.DELETE OPERATION ******************** \\
        // delete resource id 100
        ResponseEntity<EmployeeDTO> deleteResponse = sendRequest(basePathUrlWithId, HttpMethod.DELETE, null);
        // delete success
        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), updateResponse.getStatusCodeValue());
        // read again to check whether the resource is deleted
        ResponseEntity<EmployeeDTO> deleteReadResponse = sendRequest(basePathUrlWithId, HttpMethod.GET, null);
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), deleteReadResponse.getStatusCodeValue());
    }

    @Test
    void createResource_whenAlreadyExists_throwsConflictException() {

        // employee id = 200
        EmployeeDTO createDto = EmployeeDTO.builder()
                .id(200L)
                .department("IT Department")
                .name("Ponraj")
                .salary(100000)
                .build();
        // create resource with id 200
        ResponseEntity<EmployeeDTO> createResponse = sendRequest(BASE_PATH_URL, HttpMethod.POST, createDto);
        // create success, returns 201 status code
        Assertions.assertEquals(HttpStatus.CREATED.value(), createResponse.getStatusCodeValue());

        // create again resource with id 200
        ResponseEntity<EmployeeDTO> createAgainResponse = sendRequest(BASE_PATH_URL, HttpMethod.POST, createDto);
        // conflict error code
        Assertions.assertEquals(HttpStatus.CONFLICT.value(), createAgainResponse.getStatusCodeValue());
    }

    @Test
    void getResource_whenNotFound_throwsNotFoundException() {
        // url with id  (api/v1/employees/300)
        String basePathUrlWithId = BASE_PATH_URL + PATH_SEPARATOR + "300";
        ResponseEntity<EmployeeDTO> readResponse = sendRequest(basePathUrlWithId, HttpMethod.GET, null);
        // returns 404 status code
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), readResponse.getStatusCodeValue());
    }

    @Test
    void updateResource_whenNotFound_throwsNotFoundException() {
        // url with id  (api/v1/employees/300)
        String basePathUrlWithId = BASE_PATH_URL + PATH_SEPARATOR + "300";

        // employee id = 300
        EmployeeDTO updateDto = EmployeeDTO.builder()
                .id(300L)
                .department("IT Department")
                .name("Ponraj")
                .salary(100000)
                .build();
        ResponseEntity<EmployeeDTO> updateResponse = sendRequest(basePathUrlWithId, HttpMethod.PUT, updateDto);
        // returns 404 status code
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), updateResponse.getStatusCodeValue());
    }

    @Test
    void deleteResource_whenNotFound_throwsNotFoundException() {
        // url with id  (api/v1/employees/300)
        String basePathUrlWithId = BASE_PATH_URL + PATH_SEPARATOR + "300";
        ResponseEntity<EmployeeDTO> readResponse = sendRequest(basePathUrlWithId, HttpMethod.DELETE, null);
        // returns 404 status code
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), readResponse.getStatusCodeValue());
    }

    @Test
    void readAllResources_whenCalled_success() {

        // employee id = 200
        EmployeeDTO dto1 = EmployeeDTO.builder()
                .id(500L)
                .department("IT Department")
                .name("Ponraj")
                .salary(100000)
                .build();
        // create resource with id 200
        ResponseEntity<EmployeeDTO> createResponse = sendRequest(BASE_PATH_URL, HttpMethod.POST, dto1);
        Assertions.assertEquals(HttpStatus.CREATED.value(), createResponse.getStatusCodeValue());

        // employee id = 200
        EmployeeDTO dto2 = EmployeeDTO.builder()
                .id(600L)
                .department("IT Department")
                .name("Ponraj")
                .salary(100000)
                .build();
        // create again resource with id 200
        ResponseEntity<EmployeeDTO> createAgainResponse = sendRequest(BASE_PATH_URL, HttpMethod.POST, dto2);
        Assertions.assertEquals(HttpStatus.CREATED.value(), createAgainResponse.getStatusCodeValue());

        ResponseEntity<Map> readAllResponses = restTemplate.exchange(
                BASE_PATH_URL, HttpMethod.GET, new HttpEntity<>(null, withCommonHeader()), Map.class);
        // read success
        Assertions.assertEquals(HttpStatus.OK.value(), readAllResponses.getStatusCodeValue());
        Map<String, Object> body = (Map<String, Object>) readAllResponses.getBody();
        List<EmployeeDTO> employeeDTOList = (List<EmployeeDTO>) body.get(ApiConstants.PAGINATION_RESULTS);
        // assert the total number of items
        Assertions.assertEquals(2, employeeDTOList.size());
    }

    /**
     * common method to exchange requests of all operation
     *
     * @param url the url (api/v1/employees/100 or api/v1/employees)
     * @param httpMethod POST, GET, PUT, DELETE
     * @param dto the employee dto to create/update
     * @return the resonse entity
     */
    private ResponseEntity<EmployeeDTO> sendRequest(String url, HttpMethod httpMethod, EmployeeDTO dto) {
        // create http entity with dto
        HttpEntity<EmployeeDTO> httpEntity = new HttpEntity<>(dto, withCommonHeader());
        return restTemplate.exchange(url, httpMethod, httpEntity, EmployeeDTO.class);
    }

    /**
     * with common headers
     *
     * @return  headers the headers with basic auth
     **/
    protected MultiValueMap<String, String> withCommonHeader() {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Authorization", "Basic cG90YXRvOm9uaW9u");
        return headers;
    }
}
