package min.gob.ec.tracingservices.controller;

import min.gob.ec.tracingservices.config.ApplicationPropierties;
import min.gob.ec.tracingservices.repository.common.FilesRepository;
import min.gob.ec.tracingservices.service.common.CommonService;
import com.google.gson.Gson;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("common")
public class Common {

    @Autowired
    private CommonService commonService;

    @Autowired
    DataSource dataSource;

    @Autowired
    private ApplicationPropierties applicationPropierties;

    @Autowired
    private FilesRepository filesRepository;

    @RequestMapping(value = "/getDate", method = RequestMethod.GET)
    public Date getDate() {
        return commonService.getDate();
    }

    @PostMapping(value = "/uploadfile")
    @Transactional(rollbackFor = {RuntimeException.class})
    public String uploadFile(@RequestParam("file") MultipartFile file, String info) throws Exception {
        try {
            Gson gson = new Gson();
            min.gob.ec.tracingservices.model.common.Files files =
                    gson.fromJson(info, min.gob.ec.tracingservices.model.common.Files.class);

            List<min.gob.ec.tracingservices.model.common.Files> filesList = filesRepository
                    .findByEntitynameAndEntityid(files.getEntityname(), files.getEntityid());

            Optional<String> ext = Optional.ofNullable(file.getOriginalFilename())
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(file.getOriginalFilename().lastIndexOf(".") + 1));

            if (!ext.isPresent()) {
                throw new Exception("No se pudo determinar la extensión del archivo a cargar.");
            }

            String filename = files.getEntityname() + "_" + files.getEntityid() + "_" + (filesList.size() + 1) + "." + ext.get();
            filename = this.applicationPropierties.getDirectoryfiles() + filename;
            Path pt = Paths.get(filename);
            Files.copy(file.getInputStream(), pt);

            files.setPath(filename);
            filesRepository.save(files);
        } catch (Exception e) {
            throw new Exception("Error al almacenar el archivo: " + e.getMessage());
        }

        JSONObject jo = new JSONObject();
        jo.appendField("status", "200");
        jo.appendField("message", "Proceso realizado con éxito");
        return jo.toString();
    }

    @PostMapping(value = "/uploadfile1")
    @Transactional(rollbackFor = {RuntimeException.class})
    public String uploadFile(String content, String info) throws Exception {
        try {
            Gson gson = new Gson();
            min.gob.ec.tracingservices.model.common.Files files =
                    gson.fromJson(info, min.gob.ec.tracingservices.model.common.Files.class);

            List<min.gob.ec.tracingservices.model.common.Files> filesList = filesRepository
                    .findByEntitynameAndEntityid(files.getEntityname(), files.getEntityid());

            Optional<String> ext = Optional.ofNullable(files.getFilename())
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(files.getFilename().lastIndexOf(".") + 1));

            if (!ext.isPresent()) {
                throw new Exception("No se pudo determinar la extensión del archivo a cargar.");
            }

            String filename = files.getEntityname() + "_" + files.getEntityid() + "_" + (filesList.size() + 1) + "." + ext.get();
            filename = this.applicationPropierties.getDirectoryfiles() + filename;
            Path pt = Paths.get(filename);

            byte[] decodedImg = Base64.getDecoder()
                    .decode(content.getBytes(StandardCharsets.UTF_8));
            Files.write(pt, decodedImg);

            files.setPath(filename);
            filesRepository.save(files);
        } catch (Exception e) {
            throw new Exception("Error al almacenar el archivo: " + e.getMessage());
        }

        JSONObject jo = new JSONObject();
        jo.appendField("status", "200");
        jo.appendField("message", "Proceso realizado con éxito");
        return jo.toString();
    }

    @RequestMapping(value = "/downloadfile", method = RequestMethod.GET)
    public String downloadFile(@Param("id") Integer id) {
        String status = "200";
        String message = "";
        String result = "";
        String filename = "";
        String ext = "";

        try {
            min.gob.ec.tracingservices.model.common.Files files = filesRepository
                    .findById(id).orElse(new min.gob.ec.tracingservices.model.common.Files());

            if (files.getId() == 0) {
                throw new Exception("No se pudo encontrar el registro asociado.");
            }

            Path pt = Paths.get(files.getPath());

            if (!Files.exists(pt)) {
                throw new Exception("No se pudo encontrar el archivo asociado.");
            }

            Optional<String> extO = Optional.ofNullable(files.getPath())
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(files.getPath().lastIndexOf(".") + 1));

            if (!extO.isPresent()) {
                throw new Exception("No se pudo determinar la extensión del archivo a cargar.");
            }

            ext = extO.get();
            filename = files.getFilename().replace(" ", "_").concat(".").concat(ext);

            byte[] data = Files.readAllBytes(pt);
            if (data.length > 0) {
                result = Base64.getEncoder().encodeToString(data);
            }
        } catch (Exception e) {
            status = "500";
            message = e.getMessage();
        }

        JSONObject jo = new JSONObject();
        jo.appendField("status", status);
        jo.appendField("message", message);
        jo.appendField("data", result);
        jo.appendField("name", filename);
        jo.appendField("ext", ext);
        return jo.toString();
    }
}
