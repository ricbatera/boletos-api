package br.com.consultdg.boletos_api.api.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.HttpMethod;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import br.com.consultdg.boletos_api.domain.request.InputManualRequest;
import br.com.consultdg.boletos_api.domain.services.InputManualService;
import br.com.consultdg.boletos_api.domain.services.ResponseProcessService;
import br.com.consultdg.boletos_api.domain.to.ListProcessResponse;
import br.com.consultdg.boletos_api.domain.utilities.Util;

@CrossOrigin
@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private Util util;

    @Autowired
    private ResponseProcessService responseProcess;

    @Autowired
    private InputManualService inputManuaService;

    private final AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

    @GetMapping("/get-list-full")
    @PreAuthorize("hasAuthority('SCOPE_usuario')")
    public ListProcessResponse getListFull(@Param(value = "startDate") String startDate,
            @Param(value = "endDate") String endDate) {
        LocalDate hoje = LocalDate.now();
        List<LocalDate> datas = util.getDatasInicialFinalAtualLocalDate(hoje.toString());
        if (startDate == null) {
            startDate = datas.get(0).toString();
        }
        if (endDate == null) {
            endDate = datas.get(1).toString();
        }
        System.out.println("Data inicial: " + startDate + "\nDataFinal: " + endDate);

        return responseProcess.getProcessByUserId(null, startDate, endDate);
    }

    @GetMapping("/generate-presigned-url")
    @PreAuthorize("hasAuthority('SCOPE_usuario')")
    public ResponseEntity<String> generatePresignedUrl(@RequestParam String fileName) {
        try {
            // Exemplo com Amazon S3 SDK
            String bucketName = "dgconsultbucket";
            Date expiration = new Date(System.currentTimeMillis() + 3600000); // 1 hora de validade

            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,
                    fileName)
                    .withMethod(HttpMethod.PUT)
                    .withExpiration(expiration);

            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
            return ResponseEntity.ok(url.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gerar URL pre-signed");
        }

    }

    @PostMapping("/input-manual")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<String> inputManual(@RequestBody InputManualRequest request) {
        return ResponseEntity.ok(inputManuaService.imputManual(request));
    }

}
