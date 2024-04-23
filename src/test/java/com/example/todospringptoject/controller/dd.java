package com.example.todospringptoject.controller;

public class dd {
}

@Value("classpath:/fixtures/controller/customer/v1/bom/by-machine/initial/1/child-bom-112.json")
private Resource childBom112Resource;
@value("classpath:/fixtures/controller/customer/v1/dom/by-machine/initial/1/parent-bom-12.json")
private Resource parentBom12Resource;
@Value("classpath:/fixtures/controller/customer/v1/bom/by-machine/initial/1/child-bom-121.json")
private Resource childBom121Resource;
@Test
@0ispleyNane("GET - 200 - Should obtain first level BOM")
void nestedPartsByFirstLevelBonCanBe0btained() {
// Given
    Machine machind = readFromResource(machineResource, new TypeReference<>() {H):
            machineRepository.save(machine);
        Bom parentBoml = createBom(parentBoml1Resource, parent: null, machine);
        Bom childBom11 = createBom(childBom111Resource, parentBom1, machine);
        Bom childBom12 = createBom(childBom112Resource, parentBom1, machine);
        Bom parentBom2 = createBom(parentBom12Resource, parent: null, machine);
        Bon childBom21 = createBom(childBom121Resource, parentBom2, machine);
bomRepository.saveAll(List.of(parentBom1, childBom11, childBom12, parent8om2, childBom21));
        String targetMachineId = parentBoml.getMachine().getId();
        String targetParentBomId = parentBoml.getId();
        SystemUserDto systemUserDto = createSystemUserüto(machine.getCustomerCom
                1a());
        nen(userService.getCurrentUser()).thenReturn(systemUserDto);
        Unitüto unit = readFromResource(unit1Resource, new TypeReference<>() (H
        when(m2mUnitService.getAll(any())).thenReturn(Collections.singletonlist(unit));
        //@nen
        BonOto actual = given() RequestSpecification
        port(port)
.contentType(MediaType.APPLICATION_JSON_VALUE)
.accept(MediaType.APPLICATION_JSON_VALUE)
        when()
        get(BASE_PATH_CUSTOMER, targetMachineId, targetParentBomId) Response
        then() ValidatableResponse
.assertThat()
.statusCode(HttpStatus.OK.value())
                .and()
        extract() ExtractableResponse<Response>
.body() ResponseBodyExtractionOptions
.as(BomDto.class);
        Then
        List<Bom> boms = List.of(childBom11, childBom12);
        BomDto expected = bomMapper.toDto(
                parent null, parentBoml, List.of(childBomll, childBom12), bomsToUnit8yIdMap(boms)

        Денис Исхаков, [23.04.2024 13:59]
        String targetMachineId = parentBoml.getMachine().getId();
        String targetParentBomId = parentBoml.getId();
        SystemUserüto systemUserüto = createSystemUserüto(machine.getCustomerCompanyId());
        when(userService.getCurrentUser()).thenReturn(systemUserDto);
        UnitOto unit readFromResource(unitlResource, new TypeReference<>() (});
    when(m2mUnitService.getAll(any())).thenReturn(Collections.singletonList(unit));
// mhen
    BomDto actual = given() RequestSpecification
            .port(port)
            .contentType(MediaType. APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .get(BASE_PATH_CUSTOMER, targetMachineId, targetParentBomId) Response
    then() ValidatableResponse
            .assertThat()
            .statusCode(HttpStatus.OK. value())
    and()
            .extract() ExtractableResponse<Response>
    body() ResponseBodyExtractionOptions
            .as(BomDto.class);
// Then
    List<Bom> boms = List.Of(childBom11, childBom12);
    BomDto expected = bomMapper.toDto(
            parent: null, parentBom1, List.of(childBom11, childBom12), bomsToUnitByIdMap(boms)
);
    expected.get8oms().sort((a, b) -> b.getId().compareTo(a.getId()));
    actual.getBoms().sort((a, b) -> b.getId().compareTo(a.getId()));
    assertEquals(expected, actual);
    @Value("classpath:/fixtures/controller/customer/v1/bom/by-machine/initial/machine-2.json")
    private Resource machine2Resource;
    @Value("classpath:/fixtures/controller/customer/v1/bom/by-machine/initial/unit-2.json")
    private Resource unit2Resource;
    @value("classpath:/fixtures/controller/customer/v1/bom/by-machine/initial/2/parent-bom-21.json")
    private Resource parentBom21Resource;