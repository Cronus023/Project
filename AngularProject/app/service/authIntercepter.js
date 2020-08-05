const authInterceptor = angular.module('AuthInterceptor', [])
// register the interceptor as a service
authInterceptor.service('AuthInterceptor', function($q, $window) {
    return {
        request: function (config) {
            const token = localStorage.getItem("JwtToken")
            if (token != null) {
                config.headers['Authorization'] = 'Bearer ' + token;
            }
            else if(config.url != 'components/registerPage/register.html'){
                $window.location.href = '#!/login'
            }
            return config || $q.when(config);
        },

        responseError: function (response) {
            if (response.status === 403)
            {
                $location.path ('/login');
            }
            return response;
        }
    }
})

