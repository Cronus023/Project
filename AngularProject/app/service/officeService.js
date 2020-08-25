const office = angular.module('office', [])
office.factory('officeService', ['$http', '$q', '$window', function ($http, $q, $window) {
    return {
        becomeProvider: function (office) {
            const becomeRequest = {
                login: localStorage.getItem('UserLogin'),
                office: office
            }
            $http.post('http://localhost:8080/becomeProvider', becomeRequest).then(function (response) {
                $window.location.reload()
            })
        },
        getOfficesByLogin: function (login) {
            const deferred = $q.defer()
            $http.get(`http://localhost:8080/getOfficesByLogin?login=${login}`).then(function (response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        getOfficeByName: function (name) {
            const deferred = $q.defer()
            $http.get(`http://localhost:8080/getOfficeByName?name=${name}`).then(function (response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        getOffices: function () {
            const deferred = $q.defer()
            $http.get('http://localhost:8080/getOffices').then(function (response) {
                deferred.resolve(response.data)
            })
            return deferred.promise
        },
        createOffice: function (officeBody) {
            const deferred = $q.defer()

            $http.post('http://localhost:8080/createOffice', officeBody).then(function (response) {
                if (response.status === 400) {
                    deferred.resolve(response.data)
                } else {
                    $window.location.href = '#!/main'
                    $window.location.reload()
                }
            })
            return deferred.promise
        }
    }
}])