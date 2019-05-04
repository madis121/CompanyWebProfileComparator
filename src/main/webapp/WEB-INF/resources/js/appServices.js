angular.module('cwpc.services', [])
.factory('DashboardService', ['$http', 'CONSTANTS', function($http, CONSTANTS) {
	var service = {};
	
	service.getProfiles = function() {
		return $http.get('/ROOT/dashboard/getProfiles');
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
