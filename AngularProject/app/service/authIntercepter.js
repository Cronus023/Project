const authInterceptor = angular.module('AuthInterceptor', [])
// register the interceptor as a service
authInterceptor.service('AuthInterceptor', function($q, $window) {
    return {
        request: function (config) {
            const token = localStorage.getItem("JwtToken")
            if (token != null) {
                config.headers['Authorization'] = 'Bearer ' + token
            }
            return config || $q.when(config)
        },
        responseError: function (response) {
            if (response.status === 403)
            {
                console.log(response)
                $location.path ('/login')
            }
            return response
        }
    }
})

