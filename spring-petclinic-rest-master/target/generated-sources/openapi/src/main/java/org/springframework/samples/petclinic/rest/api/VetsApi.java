/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.8.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.springframework.samples.petclinic.rest.api;

import org.springframework.samples.petclinic.rest.dto.RestErrorDto;
import org.springframework.samples.petclinic.rest.dto.VetDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

<<<<<<< HEAD
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-10T17:09:27.164166257+02:00[Europe/Rome]", comments = "Generator version: 7.8.0")
=======
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-30T10:56:23.721398395+02:00[Europe/Rome]", comments = "Generator version: 7.8.0")
>>>>>>> 18d8c16c1e889e5506f573d37a9b573402be2d82
@Validated
@Tag(name = "vet", description = "Endpoints related to vets.")
public interface VetsApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /vets : Create a Vet
     * Creates a vet .
     *
     * @param vetDto The vet (required)
     * @return Vet created successfully. (status code 200)
     *         or Not modified. (status code 304)
     *         or Bad request. (status code 400)
     *         or Vet not found. (status code 404)
     *         or Server error. (status code 500)
     */
    @Operation(
        operationId = "addVet",
        summary = "Create a Vet",
        description = "Creates a vet .",
        tags = { "vet" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Vet created successfully.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = VetDto.class))
            }),
            @ApiResponse(responseCode = "304", description = "Not modified."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Vet not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Server error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/vets",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<VetDto> addVet(
        @Parameter(name = "VetDto", description = "The vet", required = true) @Valid @RequestBody VetDto vetDto
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"firstName\" : \"James\", \"lastName\" : \"Carter\", \"specialties\" : [ { \"name\" : \"radiology\", \"id\" : 1 }, { \"name\" : \"radiology\", \"id\" : 1 } ], \"id\" : 1 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"path\" : \"/api/owners\", \"trace\" : \"com.atlassian.oai.validator.springmvc.InvalidRequestException: ...\", \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"error\" : \"Bad Request\", \"message\" : \"Request failed schema validation\", \"status\" : 400, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"path\" : \"/api/owners\", \"trace\" : \"com.atlassian.oai.validator.springmvc.InvalidRequestException: ...\", \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"error\" : \"Bad Request\", \"message\" : \"Request failed schema validation\", \"status\" : 400, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"path\" : \"/api/owners\", \"trace\" : \"com.atlassian.oai.validator.springmvc.InvalidRequestException: ...\", \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"error\" : \"Bad Request\", \"message\" : \"Request failed schema validation\", \"status\" : 400, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /vets/{vetId} : Delete a vet by ID
     * Returns the vet or a 404 error.
     *
     * @param vetId The ID of the vet. (required)
     * @return Vet details found and returned. (status code 200)
     *         or Not modified. (status code 304)
     *         or Bad request. (status code 400)
     *         or Vet not found. (status code 404)
     *         or Server error. (status code 500)
     */
    @Operation(
        operationId = "deleteVet",
        summary = "Delete a vet by ID",
        description = "Returns the vet or a 404 error.",
        tags = { "vet" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Vet details found and returned.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = VetDto.class))
            }),
            @ApiResponse(responseCode = "304", description = "Not modified."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Vet not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Server error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/vets/{vetId}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<VetDto> deleteVet(
        @Min(0) @Parameter(name = "vetId", description = "The ID of the vet.", required = true, in = ParameterIn.PATH) @PathVariable("vetId") Integer vetId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"firstName\" : \"James\", \"lastName\" : \"Carter\", \"specialties\" : [ { \"name\" : \"radiology\", \"id\" : 1 }, { \"name\" : \"radiology\", \"id\" : 1 } ], \"id\" : 1 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"path\" : \"/api/owners\", \"trace\" : \"com.atlassian.oai.validator.springmvc.InvalidRequestException: ...\", \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"error\" : \"Bad Request\", \"message\" : \"Request failed schema validation\", \"status\" : 400, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"path\" : \"/api/owners\", \"trace\" : \"com.atlassian.oai.validator.springmvc.InvalidRequestException: ...\", \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"error\" : \"Bad Request\", \"message\" : \"Request failed schema validation\", \"status\" : 400, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"path\" : \"/api/owners\", \"trace\" : \"com.atlassian.oai.validator.springmvc.InvalidRequestException: ...\", \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"error\" : \"Bad Request\", \"message\" : \"Request failed schema validation\", \"status\" : 400, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /vets/{vetId} : Get a vet by ID
     * Returns the vet or a 404 error.
     *
     * @param vetId The ID of the vet. (required)
     * @return Vet details found and returned. (status code 200)
     *         or Not modified. (status code 304)
     *         or Bad request. (status code 400)
     *         or Vet not found. (status code 404)
     *         or Server error. (status code 500)
     */
    @Operation(
        operationId = "getVet",
        summary = "Get a vet by ID",
        description = "Returns the vet or a 404 error.",
        tags = { "vet" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Vet details found and returned.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = VetDto.class))
            }),
            @ApiResponse(responseCode = "304", description = "Not modified."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Vet not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Server error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/vets/{vetId}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<VetDto> getVet(
        @Min(0) @Parameter(name = "vetId", description = "The ID of the vet.", required = true, in = ParameterIn.PATH) @PathVariable("vetId") Integer vetId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"firstName\" : \"James\", \"lastName\" : \"Carter\", \"specialties\" : [ { \"name\" : \"radiology\", \"id\" : 1 }, { \"name\" : \"radiology\", \"id\" : 1 } ], \"id\" : 1 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"path\" : \"/api/owners\", \"trace\" : \"com.atlassian.oai.validator.springmvc.InvalidRequestException: ...\", \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"error\" : \"Bad Request\", \"message\" : \"Request failed schema validation\", \"status\" : 400, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"path\" : \"/api/owners\", \"trace\" : \"com.atlassian.oai.validator.springmvc.InvalidRequestException: ...\", \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"error\" : \"Bad Request\", \"message\" : \"Request failed schema validation\", \"status\" : 400, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"path\" : \"/api/owners\", \"trace\" : \"com.atlassian.oai.validator.springmvc.InvalidRequestException: ...\", \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"error\" : \"Bad Request\", \"message\" : \"Request failed schema validation\", \"status\" : 400, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /vets : Lists vets
     * Returns an array of vets.
     *
     * @return Vets found and returned. (status code 200)
     *         or Not modified. (status code 304)
     *         or Server error. (status code 500)
     */
    @Operation(
        operationId = "listVets",
        summary = "Lists vets",
        description = "Returns an array of vets.",
        tags = { "vet" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Vets found and returned.", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VetDto.class)))
            }),
            @ApiResponse(responseCode = "304", description = "Not modified."),
            @ApiResponse(responseCode = "500", description = "Server error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/vets",
        produces = { "application/json" }
    )
    
    default ResponseEntity<List<VetDto>> listVets(
        
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"firstName\" : \"James\", \"lastName\" : \"Carter\", \"specialties\" : [ { \"name\" : \"radiology\", \"id\" : 1 }, { \"name\" : \"radiology\", \"id\" : 1 } ], \"id\" : 1 }, { \"firstName\" : \"James\", \"lastName\" : \"Carter\", \"specialties\" : [ { \"name\" : \"radiology\", \"id\" : 1 }, { \"name\" : \"radiology\", \"id\" : 1 } ], \"id\" : 1 } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"path\" : \"/api/owners\", \"trace\" : \"com.atlassian.oai.validator.springmvc.InvalidRequestException: ...\", \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"error\" : \"Bad Request\", \"message\" : \"Request failed schema validation\", \"status\" : 400, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PUT /vets/{vetId} : Update a vet  by ID
     * Returns the vet or a 404 error.
     *
     * @param vetId The ID of the vet. (required)
     * @param vetDto The vet (required)
     * @return Pet type details found and returned. (status code 200)
     *         or Not modified. (status code 304)
     *         or Bad request. (status code 400)
     *         or Vet not found. (status code 404)
     *         or Server error. (status code 500)
     */
    @Operation(
        operationId = "updateVet",
        summary = "Update a vet  by ID",
        description = "Returns the vet or a 404 error.",
        tags = { "vet" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Pet type details found and returned.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = VetDto.class))
            }),
            @ApiResponse(responseCode = "304", description = "Not modified."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Vet not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Server error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/vets/{vetId}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<VetDto> updateVet(
        @Min(0) @Parameter(name = "vetId", description = "The ID of the vet.", required = true, in = ParameterIn.PATH) @PathVariable("vetId") Integer vetId,
        @Parameter(name = "VetDto", description = "The vet", required = true) @Valid @RequestBody VetDto vetDto
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"firstName\" : \"James\", \"lastName\" : \"Carter\", \"specialties\" : [ { \"name\" : \"radiology\", \"id\" : 1 }, { \"name\" : \"radiology\", \"id\" : 1 } ], \"id\" : 1 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"path\" : \"/api/owners\", \"trace\" : \"com.atlassian.oai.validator.springmvc.InvalidRequestException: ...\", \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"error\" : \"Bad Request\", \"message\" : \"Request failed schema validation\", \"status\" : 400, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"path\" : \"/api/owners\", \"trace\" : \"com.atlassian.oai.validator.springmvc.InvalidRequestException: ...\", \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"error\" : \"Bad Request\", \"message\" : \"Request failed schema validation\", \"status\" : 400, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"path\" : \"/api/owners\", \"trace\" : \"com.atlassian.oai.validator.springmvc.InvalidRequestException: ...\", \"schemaValidationErrors\" : [ { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" }, { \"message\" : \"[Path '/lastName'] Instance type (null) does not match any allowed primitive type (allowed: ['string'])\" } ], \"error\" : \"Bad Request\", \"message\" : \"Request failed schema validation\", \"status\" : 400, \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
