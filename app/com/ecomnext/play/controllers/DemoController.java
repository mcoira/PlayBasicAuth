package com.ecomnext.play.controllers;

import com.ecomnext.play.auth.HttpBasicAuth;
import play.*;
import play.mvc.*;

import views.html.*;

public class DemoController extends Controller {

    @HttpBasicAuth
    public static Result index() {
        return ok();
    }

}
