package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerMVC {
	
	
	@RequestMapping(value = { "/index", "/" }, method = RequestMethod.GET)
	public ModelAndView viewHomePage() {

		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		return model;

	}

}
