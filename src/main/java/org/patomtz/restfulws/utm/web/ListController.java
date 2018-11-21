package org.patomtz.restfulws.utm.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.patomtz.restfulws.utm.service.FileService;
import org.patomtz.restfulws.utm.view.DownloadView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

@Controller
public class ListController {
	@Autowired
	private FileService fileService;
	
	private static final Logger log = LogManager.getLogger();
	
	@RequestMapping(value = "list/files", method = RequestMethod.GET)
	@ResponseBody
	public View getFile(@RequestParam("fileName")  String fileName) throws IOException {
		Path attachment = this.fileService.getFile(fileName);
		log.info("Descargando {}", attachment.toAbsolutePath().toString());
		return new DownloadView(attachment.getFileName().toString(), Files.probeContentType(attachment), Files.readAllBytes(attachment));
	}
	
	@RequestMapping(value="list", method = RequestMethod.GET)
	public String doFileList() {
		return "download/file";
	}

	@RequestMapping(value = "list/show", method = RequestMethod.POST)
	public String getFileList(Map<String, Object> model, 
			HttpSession session, HttpServletRequest request,
			@RequestParam("path") String path) {
		List<String> errors = new ArrayList<String>();
		List<String> warnings = new ArrayList<String>();
		Path dir = Paths.get(path);
		List<Path> paths = new ArrayList<>();
		if (Files.exists(dir) && Files.isDirectory(dir)) {
			this.fileService.walkDir(dir, paths);
			model.put("path", path);
			model.put("paths", paths);
			model.put("warnings", warnings);
		} else {
			errors.add(String.format("El path %s no existe o no es directorio", path));
			model.put("errors", errors);
		}
		return "download/list";
	}
}