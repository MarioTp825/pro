import FetchService from "/FetchService.js"

const fetchService = new FetchService()

function buildHeaders(authorization = null) {
    return {
        "Content-Type": "application/json",
        "Authorization": (authorization) ? authorization : "Bearer TOKEN_MISSING"
    }
}

function buildJsonFormData(form) {
    const formData = { }
    for (const pair of new FormData(form)) {
        formData[pair[0]] = pair[1]
    }
    return formData
}

async function submitForm(e, obj) {
    e.preventDefault()
    const btn = document.getElementById('loginSubmit')
    btn.disable = true
    setTimeout(() => btn.disable = false, 2000)

    const jsonData = buildJsonFormData(obj)

    const heathers = buildHeaders()

    const response = await fetchService.performPostHttpRequest('/doLogin', heathers, jsonData)
    console.log(response)
}

const loginForm = document.querySelector('#loginForm')
if (loginForm) {
    loginForm.addEventListener('submit', function (e) {
        submitForm(e, this)
    })
}