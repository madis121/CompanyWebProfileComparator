angular.module('cwpc.services', [])
.factory('DashboardService', ['$http', 'CONSTANTS', function($http, CONSTANTS) {
	var service = {};
	
	service.getProfiles = function() {
		return $http.get('/ROOT/dashboard/getProfiles');
	}
	
	service.getProfile = function(data) {
		return $http({
			url: '/ROOT/dashboard/getProfile',
			method: 'GET',
			params: data
		});
	}
	
	service.createProfile = function(data) {
		return $http({
			url: '/ROOT/dashboard/createProfile',
			method: 'POST',
			params: data
		});
	}
	
	service.updateProfile = function(data) {
		return $http({
			url: '/ROOT/dashboard/updateProfile',
			method: 'POST',
			params: data
		});
	}
	
	service.deleteProfile = function(data) {
		return $http({
			url: '/ROOT/dashboard/deleteProfile',
			method: 'POST',
			params: data
		});
	}
	
	service.collectData = function(data) {
		return $http.get('/ROOT/dashboard/collectData', data);
	}
	
	return service;
}])
.factory('MessageService', ['$http', 'CONSTANTS', function($http, CONSTANTS) {
	var service = {};
	
	service.getTranslations = function() {
		return $http.get('/ROOT/messageBundle');
	}
	
	return service;
}]);
