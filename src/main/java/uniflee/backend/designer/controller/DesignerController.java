package uniflee.backend.designer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import uniflee.backend.designer.service.DesignerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/designer")
public class DesignerController {
	private final DesignerService designerService;
}
