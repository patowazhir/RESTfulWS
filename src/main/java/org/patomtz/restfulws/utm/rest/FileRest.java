package org.patomtz.restfulws.utm.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Map;
import org.patomtz.restfulws.utm.model.OptionsDoc;
import org.patomtz.restfulws.utm.rest.exception.ResourceNotFoundException;
import org.patomtz.restfulws.utm.service.FileService;
import org.patomtz.restfulws.utm.view.DownloadView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class FileRest {
	@Autowired
	FileService fileService;
	
	@RequestMapping(value = "file", method = RequestMethod.OPTIONS)
	public ResponseEntity<?> showOptions() {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Allow", "OPTIONS,GET,POST,DELETE");
            Map<HttpMethod, String> methods = new Hashtable<>(4);
            methods.put(HttpMethod.GET, "Descarga el archivo especificado");
            methods.put(HttpMethod.OPTIONS, "Documentacion.");
            methods.put(HttpMethod.POST, "Sube el archivo especificado");
            methods.put(HttpMethod.DELETE, "Borra el archivo especificado");
            OptionsDoc options = new OptionsDoc();
            options.setMethods(methods);
            return new ResponseEntity<>(options, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "file", params = {"path"}, method = RequestMethod.GET)
	@ResponseBody
	public View downloadFile(@RequestParam(value = "path") String path) throws IOException {
            if(!Files.exists(Paths.get(path)))
                    throw new ResourceNotFoundException(path + " does not exist.");
            Path file = fileService.getFile(path);
            return new DownloadView(file.getFileName().toString(), Files.probeContentType(file), Files.readAllBytes(file));
	}
	
	@RequestMapping(value = "file", params = {"path"}, method = RequestMethod.DELETE)
	@ResponseBody
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String deleteFile(@RequestParam(value = "path") String path) {
            if(!Files.exists(Paths.get(path)))
                    throw new ResourceNotFoundException(path + " does not exist.");
            return fileService.delete(path);
	}
	
	@RequestMapping(value = "file", method = RequestMethod.POST)
	public ResponseEntity<?> uploadFile(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "name") String name, @RequestParam(value = "dir") String dir) {
		if(file.isEmpty())
			return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
		fileService.uploadFile(file, name, dir);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", ServletUriComponentsBuilder.fromCurrentServletMapping().path("/file/?path="+dir+"/"+name).build().toString());
		return new ResponseEntity<>(null, headers, HttpStatus.CREATED);
	}
}