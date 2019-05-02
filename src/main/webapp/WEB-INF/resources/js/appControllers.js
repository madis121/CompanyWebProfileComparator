var module = angular.module('cwpc.controllers', []);

module.controller('DashboardController', ['$scope', 'CONSTANTS', 'DashboardService', function($scope, CONSTANTS, DashboardService) {
	$scope.profiles = [];
	$scope.websiteIndex = 0;
	$scope.websites = [{i: $scope.websiteIndex, url: ''}];
	
	$scope.profileOption = 1;
	
	$scope.generatedProfile = {
		name: '',
		keywords: '',
		urls: ''
	};
	
	$scope.cleanProfile = {
		name: '',
		keywords: '',
		urls: ''
	};
	
	$scope.dom = {
		profileOptions: {isShow: true},
		newProfileContent: {isShow: true},
		newProfileSpinner: {isShow: false},
		newProfileResult: {isShow: false},
		newProfileClean: {isShow: false},
		newProfileButtons: {isShow: false}
	};
	
	DashboardService.getProfiles().then(function(response) {
		$scope.profiles = response.data.profiles;
	});
	
	// TODO: replace jQuery?
	$scope.openProfileModal = function(profileId) {
	    $('#profileEditModal').empty();
	    $('#profileEditModal').load('open-profile?id=' + profileId);
	    $('#profileEditModal').modal('show');
	}
	
	$scope.chooseProfileCreation = function() {
		if ($scope.profileOption == 1) {
			$scope.dom.newProfileClean.isShow = false;
			// TODO: replace jQuery?
			if ($('#createProfile [name="keywords"]').tagsinput('items').length > 0) {
				$scope.dom.newProfileContent.isShow = false;
				$scope.dom.newProfileResult.isShow = true;
				$scope.dom.newProfileButtons.isShow = true;
			} else {
				$scope.dom.newProfileResult.isShow = false;
				$scope.dom.newProfileButtons.isShow = false;
				$scope.dom.newProfileContent.isShow = true;
			}
		} else if ($scope.profileOption == 2) {
			$scope.dom.newProfileContent.isShow = false;
			$scope.dom.newProfileResult.isShow = false;
			$scope.dom.newProfileClean.isShow = true;
			$scope.dom.newProfileButtons.isShow = true;
		}
		console.log($scope.dom);
	}
	
	$scope.appendInput = function() {
		$scope.websiteIndex++;
		$scope.websites.push({i: $scope.websiteIndex, url: ''});
		$scope.dom.newProfileSpinner = true;
	}
	
	$scope.removeInput = function(index) {
		$scope.websites.map(function(obj, i) {
			if (obj.i == index) {
				return $scope.websites.splice(i, 1);
			}
		});
	}
	
	$scope.collectData = function() {
		var expression = '^(?!mailto:)(?:(?:http|https|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?:(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[0-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,})))|localhost)(?::\\d{2,5})?(?:(/|\\?|#)[^\\s]*)?$';
		var regex = new RegExp(expression, "i");
		var validationError = false;
		var params = {params: {website: []}};
		
		angular.forEach($scope.websites, function(website, i) {
			params.params.website.push(website.url);
			if (!regex.test(website.url)) {
				//addValidationErrorMessage($(this), '<spring:message code="new.profile.modal.url.validation.error" />');
				console.log('validation error');
				validationError = true;
				return false;
			} else {
				removeValidationErrorMessage($(this));
			}
		});
		
		if (!validationError) {
			$scope.dom.profileOptions.isShow = false;
			$scope.dom.newProfileContent.isShow = false;
			$scope.dom.newProfileSpinner.isShow = true;
			
			DashboardService.collectData(params).then(function(response) {
				var collectedData = response.data.collectedData;
				angular.forEach(collectedData.keywords, function(keyword, i) {
					// TODO: replace jQuery?
					$('#createProfile [name="keywords"]').tagsinput('add', keyword.word);
				});
				$scope.generatedProfile.urls = collectedData.websites.toString();
				$scope.dom.newProfileSpinner.isShow = false;
				$scope.dom.profileOptions.isShow = true;
				$scope.dom.newProfileResult.isShow = true;
				$scope.dom.newProfileButtons.isShow = true;
				console.log($scope.dom);
			});
		}
	}
	
	$scope.clearProfile = function() {
		if ($scope.profileOption == 1) {
			$scope.websites = [{i: $scope.websiteIndex, url: ''}];
			$scope.generatedProfile = {
				name: '',
				keywords: '',
				urls: ''
			};
			// TODO: replace jQuery?
			$('#createProfile [name="keywords"]').tagsinput('removeAll');
			$scope.dom.newProfileResult.isShow = false;
			$scope.dom.newProfileButtons.isShow = false;
			$scope.dom.newProfileContent.isShow = true;
		} else if ($scope.profileOption == 2) {
			$scope.cleanProfile = {
				name: '',
				keywords: '',
				urls: ''
			};
			// TODO: replace jQuery?
			$('#createProfileClean [name="keywords"]').tagsinput('removeAll');
		}
		console.log($scope.dom);
	}
}]);