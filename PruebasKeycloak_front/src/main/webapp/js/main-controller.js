'use strict';

appControllers.controller('MainController', ['$rootScope', '$scope', '$http', 'authorization',
    function($rootScope, $scope, $http, authorization) {
        $scope.status = 'running...';
        $scope.profile = authorization.profile;
        $scope.isAdmin = authorization.hasRealmRole('ADMIN')
        $scope.isManager = authorization.hasRealmRole('MANAGER')
        $scope.isUsuario = authorization.hasRealmRole('USER')
        
        $scope.getPrincipal = function() {
        	$http.get("http://japerezlocal:8088/PruebasKeycloak_back/").success(function(data) {
            	$scope.contracts = data;
            });
        }
        
        $scope.getConsulta = function() {
        	$http.get("http://japerezlocal:8088/PruebasKeycloak_back/consulta").success(function(data) {
            	$scope.contracts = data;
            });
        }
        
        $scope.getModificacion = function() {
        	$http.get("http://japerezlocal:8088/PruebasKeycloak_back/modificacion").success(function(data) {
            	$scope.contracts = data;
            });
        }
        
        $scope.logout = function() {
        	authorization.logout();
        }
    }
]);