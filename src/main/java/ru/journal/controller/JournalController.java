package ru.journal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.journal.model.dto.JournalFilter;
import ru.journal.model.dto.JournalResponse;
import ru.journal.service.CBRService;

import java.util.List;

@Tag(name = "Journal controller", description = "The Journal API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/journal")
public class JournalController {
    private final CBRService cbrService;

    @Operation(summary = "Ручной запуск синхронизации курса валют с ЦБ")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешно")
    })
    @PostMapping("/update")
    public ResponseEntity<String> updateRates(){
        if (cbrService.updateRates()) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error parsing XML response");
    }

    @Operation(summary = "Чтение журнала с фильтрацией, пагинацией и сортировкой по параметрам")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Курсы валют найдены",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = JournalResponse.class)))
                    })
    })
    @GetMapping()
    public ResponseEntity<List<JournalResponse>> findRates(
            JournalFilter filter,
            @RequestParam(defaultValue = "0") @Parameter(description = "Страница", example = "0") int page,
            @RequestParam(defaultValue = "5") @Parameter(description = "Количество элементов на странице", example = "5") int size,
            @RequestParam(defaultValue = "rateDate:desc") @Parameter(description = "Сортировка по параметрам", example = "rateDate:asc") String[] sort) {
        return cbrService.handleRates(filter, page, size, sort);
    }
}
