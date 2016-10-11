var module = angular.module('product', []);

var auth = {};
var logout = function(){
    console.log('*** LOGOUT');

//    auth.logoutUrl = auth.authz.authServerUrl + "/realms/" + auth.authz.realm + "/tokens/logout?redirect_uri=" + encodeURIComponent("http://japerezlocal:7005/PruebasKeycloak_front/");
//    console.log(auth.logoutUrl);
//    window.location = auth.logoutUrl;
    
    auth.authz.logout().success(function () {
    	console.log('*** LOGOUT-OK');
    }).error(function(){
    	console.log('*** LOGOUT-error');
    });
};

var login = function(){
    console.log('*** LOGIN');

    auth.authz.login().success(function () {
        console.log('*** LOGIN-success');
        auth.loggedIn = true;
        auth.authz = keycloakAuth;
        console.log(auth.authz.hasRealmRole('user'));
        console.log(auth.authz.hasRealmRole('admin'));
        auth.logoutUrl = keycloakAuth.authServerUrl + "/realms/" + keycloakAuth.realm + "/tokens/logout?redirect_uri=" + encodeURIComponent("http://japerezlocal:7005/PruebasKeycloak_front/");
        module.factory('Auth', function() {
            return auth;
        });
    }).error(function () {
    	console.log('*** LOGIN-error');
    });
};


angular.element(document).ready(function ($http) {
    var keycloakAuth = new Keycloak('keycloak/keycloak.json');
    //auth.loggedIn = false;

//    keycloakAuth.init({ onLoad: 'check-sso' }/*{ onLoad: 'login-required' }*/).success(function (authenticated) {
    keycloakAuth.init({ onLoad: 'login-required' }).success(function (authenticated) {
        auth.loggedIn = authenticated;
        auth.authz = keycloakAuth;
        console.log('Authenticated: ' + authenticated);
        auth.authz.loadUserProfile().success(function(profile){
        	auth.username = profile.username;
        	console.log(auth.username);
        });
        console.log(auth.authz.hasRealmRole('user'));
        console.log(auth.authz.hasRealmRole('admin'));
//        auth.logoutUrl = keycloakAuth.authServerUrl + "/realms/" + keycloakAuth.realm + "/tokens/logout?redirect_uri=http://localhost:7005";
        module.factory('Auth', function() {
            return auth;
        });
        if(authenticated){
        	 auth.authz.loadUserProfile().success(function(profile){
             	auth.username = profile.username;
             	console.log(auth.username);
             	angular.bootstrap(document, ["product"]);
             });
        } else {
        	angular.bootstrap(document, ["product"]);
        }
    }).error(function () {
            window.location.reload();
        });

    keycloakAuth.onAuthSuccess = function() { console.log('onAuthSuccess'); };
    keycloakAuth.onAuthRefreshSuccess = function() { console.log('onAuthRefreshSuccess'); };
});

module.controller('GlobalAuthCtrl', function($scope, $http) {
	$scope.username = auth.username;
	$scope.auth = auth;
	console.log($scope.username);
	console.log($scope.auth.authz.hasRealmRole('USER'));
});


module.controller('GlobalCtrl', function($scope, $http) {
   // $scope.products = [];
    $scope.message ={};
    $scope.index = function(){
    	$http.get("http://japerezlocal:9000/PruebasKeycloak_back/").success(function(data) {
        	console.log( data );
        	 $scope.message = angular.fromJson(data);
        }).error(function(){
        	console.log( 'Error conexion backend');
        });
    };
    $scope.consulta = function(){
    	$http.get("http://japerezlocal:9000/PruebasKeycloak_back/consulta").success(function(data) {
        	console.log( data );
        	 $scope.message = angular.fromJson(data);
        }).error(function(){
        	console.log( 'Error conexion backend');
        });
    };
    $scope.modificacion = function(){
    	$http.get("http://japerezlocal:9000/PruebasKeycloak_back/modificacion").success(function(data) {
        	console.log( data );
        	 $scope.message = angular.fromJson(data);
        }).error(function(){
        	console.log( 'Error conexion backend');
        });
    };
    $scope.logout = logout;
    $scope.login = login;
});


module.factory('authInterceptor', function($q, Auth) {
    return {
        request: function (config) {
            var deferred = $q.defer();
            if (Auth.authz.token) {
                Auth.authz.updateToken(5).success(function() {
                    config.headers = config.headers || {};
                    config.headers.Authorization = 'Bearer ' + Auth.authz.token;

                    deferred.resolve(config);
                }).error(function() {
                        deferred.reject('Failed to refresh token');
                    });
            }
            return deferred.promise;
        }
    };
});




module.config(function($httpProvider) {
    $httpProvider.responseInterceptors.push('errorInterceptor');
    $httpProvider.interceptors.push('authInterceptor');

});

module.factory('errorInterceptor', function($q) {
    return function(promise) {
        return promise.then(function(response) {
            return response;
        }, function(response) {
            if (response.status == 401) {
                console.log('session timeout?');
                logout();
            } else if (response.status == 403) {
                alert("Forbidden");
            } else if (response.status == 404) {
                alert("Not found");
            } else if (response.status) {
                if (response.data && response.data.errorMessage) {
                    alert(response.data.errorMessage);
                } else {
                    alert("An unexpected server error has occurred");
                }
            }
            return $q.reject(response);
        });
    };
});