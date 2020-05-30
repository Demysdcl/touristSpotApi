package com.restapi.touristspot.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ObjectNotFoundException::class)
    fun handleObjectNotFound(e: ObjectNotFoundException, request: HttpServletRequest) =
            createResponseEntity(e, request, HttpStatus.NOT_FOUND)

    @ExceptionHandler(ObjectAlreadyExistsException::class)
    fun handleObjectAlreadyExist(e: ObjectAlreadyExistsException, request: HttpServletRequest) =
            createResponseEntity(e, request, HttpStatus.CONFLICT)


    private fun createResponseEntity(e: RuntimeException, request: HttpServletRequest, httpStatus: HttpStatus): ResponseEntity<StandardErrorResponse> {
        return ResponseEntity
                .status(httpStatus)
                .body(createStandardError(e, request, httpStatus.value()))
    }

    private fun createStandardError(e: RuntimeException, request: HttpServletRequest, httpStatus: Int): StandardErrorResponse {
        return StandardErrorResponse(
                System.currentTimeMillis(),
                httpStatus,
                "Not found",
                e.message ?: "",
                request.requestURI
        )
    }
}