/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.8.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.springframework.samples.petclinic.rest.api;

import org.springframework.samples.petclinic.rest.dto.RestErrorDto;
import org.springframework.samples.petclinic.rest.dto.VisitDto;
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

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-30T10:56:23.721398395+02:00[Europe/Rome]", comments = "Generator version: 7.8.0")
@Validated
@Tag(name = "visit", description = "Endpoints related to vet visits.")
public interface VisitsApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /visits : Create a visit
     * Creates a visit.
     *
     * @param visitDto The visit (required)
     * @return visit created successfully. (status code 200)
     *         or Not modified. (status code 304)
     *         or Bad request. (status code 400)
     *         or Visit not found. (status code 404)
     *         or Server error. (status code 500)
     */
    @Operation(
        operationId = "addVisit",
        summary = "Create a visit",
        description = "Creates a visit.",
        tags = { "visit" },
        responses = {
            @ApiResponse(responseCode = "200", description = "visit created successfully.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = VisitDto.class))
            }),
            @ApiResponse(responseCode = "304", description = "Not modified."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Visit not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Server error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/visits",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<VisitDto> addVisit(
        @Parameter(name = "VisitDto", description = "The visit", required = true) @Valid @RequestBody VisitDto visitDto
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"date\" : \"2013-01-01\", \"petId\" : 1, \"description\" : \"rabies shot\", \"id\" : 1 }";
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
     * DELETE /visits/{visitId} : Delete a visit by ID
     * Returns the visit or a 404 error.
     *
     * @param visitId The ID of the visit. (required)
     * @return Visit details found and returned. (status code 200)
     *         or Not modified. (status code 304)
     *         or Bad request. (status code 400)
     *         or Visit not found. (status code 404)
     *         or Server error. (status code 500)
     */
    @Operation(
        operationId = "deleteVisit",
        summary = "Delete a visit by ID",
        description = "Returns the visit or a 404 error.",
        tags = { "visit" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Visit details found and returned.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = VisitDto.class))
            }),
            @ApiResponse(responseCode = "304", description = "Not modified."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Visit not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Server error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/visits/{visitId}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<VisitDto> deleteVisit(
        @Min(0) @Parameter(name = "visitId", description = "The ID of the visit.", required = true, in = ParameterIn.PATH) @PathVariable("visitId") Integer visitId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"date\" : \"2013-01-01\", \"petId\" : 1, \"description\" : \"rabies shot\", \"id\" : 1 }";
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
     * GET /visits/{visitId} : Get a visit by ID
     * Returns the visit or a 404 error.
     *
     * @param visitId The ID of the visit. (required)
     * @return Visit details found and returned. (status code 200)
     *         or Not modified. (status code 304)
     *         or Bad request. (status code 400)
     *         or Visit not found. (status code 404)
     *         or Server error. (status code 500)
     */
    @Operation(
        operationId = "getVisit",
        summary = "Get a visit by ID",
        description = "Returns the visit or a 404 error.",
        tags = { "visit" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Visit details found and returned.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = VisitDto.class))
            }),
            @ApiResponse(responseCode = "304", description = "Not modified."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Visit not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Server error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/visits/{visitId}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<VisitDto> getVisit(
        @Min(0) @Parameter(name = "visitId", description = "The ID of the visit.", required = true, in = ParameterIn.PATH) @PathVariable("visitId") Integer visitId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"date\" : \"2013-01-01\", \"petId\" : 1, \"description\" : \"rabies shot\", \"id\" : 1 }";
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
     * GET /visits : Lists visits
     * Returns an array of visit .
     *
     * @return visits found and returned. (status code 200)
     *         or Not modified. (status code 304)
     *         or Server error. (status code 500)
     */
    @Operation(
        operationId = "listVisits",
        summary = "Lists visits",
        description = "Returns an array of visit .",
        tags = { "visit" },
        responses = {
            @ApiResponse(responseCode = "200", description = "visits found and returned.", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = VisitDto.class)))
            }),
            @ApiResponse(responseCode = "304", description = "Not modified."),
            @ApiResponse(responseCode = "500", description = "Server error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/visits",
        produces = { "application/json" }
    )
    
    default ResponseEntity<List<VisitDto>> listVisits(
        
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"date\" : \"2013-01-01\", \"petId\" : 1, \"description\" : \"rabies shot\", \"id\" : 1 }, { \"date\" : \"2013-01-01\", \"petId\" : 1, \"description\" : \"rabies shot\", \"id\" : 1 } ]";
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
     * PUT /visits/{visitId} : Update a visit by ID
     * Returns the visit or a 404 error.
     *
     * @param visitId The ID of the visit. (required)
     * @param visitDto The visit (required)
     * @return Visit details found and returned. (status code 200)
     *         or Not modified. (status code 304)
     *         or Bad request. (status code 400)
     *         or Visit not found. (status code 404)
     *         or Server error. (status code 500)
     */
    @Operation(
        operationId = "updateVisit",
        summary = "Update a visit by ID",
        description = "Returns the visit or a 404 error.",
        tags = { "visit" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Visit details found and returned.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = VisitDto.class))
            }),
            @ApiResponse(responseCode = "304", description = "Not modified."),
            @ApiResponse(responseCode = "400", description = "Bad request.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Visit not found.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            }),
            @ApiResponse(responseCode = "500", description = "Server error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = RestErrorDto.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/visits/{visitId}",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<VisitDto> updateVisit(
        @Min(0) @Parameter(name = "visitId", description = "The ID of the visit.", required = true, in = ParameterIn.PATH) @PathVariable("visitId") Integer visitId,
        @Parameter(name = "VisitDto", description = "The visit", required = true) @Valid @RequestBody VisitDto visitDto
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"date\" : \"2013-01-01\", \"petId\" : 1, \"description\" : \"rabies shot\", \"id\" : 1 }";
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
