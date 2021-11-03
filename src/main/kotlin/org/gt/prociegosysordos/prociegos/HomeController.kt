package org.gt.prociegosysordos.prociegos

import org.gt.prociegosysordos.prociegos.model.LoginSchema
import org.gt.prociegosysordos.prociegos.repository.FireBaseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@Controller
class HomeController {
    @Autowired
    private lateinit var repository: FireBaseRepository
    private var wrongPassword = false
    private var response: LoginSchema.LoginResponse? = null

    @GetMapping("/")
    fun home(model: Model):String {
        val flag = false
        return if (flag) {
            "redirect:/dashboard/1"
        } else {
            "redirect:/login"
        }
    }

    @PostMapping("/doLogin")
    fun doLogin(@ModelAttribute login: LoginSchema.Login, model: Model): String{
        model.addAttribute("login", login)
        val response = repository.login(login)
        val flag = response.isSuccessful
        return if (flag && response.videos!!.isNotEmpty()) {
            this.response = response
            "redirect:/dashboard/${response.videos.first().id}"
        } else {
            wrongPassword = true
            "redirect:/login"
        }
    }

    @GetMapping("/dashboard/{id}")
    fun dashboard( model: Model, @PathVariable("id") video: Int): String {
        model["title"] = "Dashboard"
        model["app"] = "dashboardapp"
        model["videos"] = response!!.videos!!
        model["current"] = repository.getVideo(video.toString())
        return "dashboard"
    }

    @GetMapping("/login")
    fun login(model: Model):String {
        var flag = false
        return if (flag) {
            model["title"] = "Dashboard"
            model["app"] = "dashboardapp"
            "redirect:/dashboard/1"
        } else {
            model["title"] = "Login"
            model["app"] = "loginapp"
            model["login"] = LoginSchema.Login("", "")
            model["fail"] = if(wrongPassword) "visible" else "invisible"
            wrongPassword = false
            "login"
        }
    }
}