var module = angular.module('cwpc.controllers', []);

module.controller('DashboardController', ['$scope', 'CONSTANTS', 'DashboardService', 'MessageService', function($scope, CONSTANTS, DashboardService, MessageService) {
	$scope.translationMessages = {};
	$scope.profiles = [];
	$scope.websiteIndex = 0;
	$scope.websites = [{i: $scope.websiteIndex, url: ''}];
	$scope.urlRegex = '^(?!mailto:)(?:(?:http|https|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?:(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])' +
			'(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[0-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]+-?)*' + 
			'[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]+-?)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,})))|localhost)' + 
			'(?::\\d{2,5})?(?:(/|\\?|#)[^\\s]*)?$';
	
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
	
	$scope.$watch('$viewContentLoaded', function() {
		MessageService.getTranslations().then(function(response) {
			$scope.translationMessages = response.data;
		});
		
		DashboardService.getProfiles().then(function(response) {
			$scope.profiles = response.data.profiles;
		});
	});
	
//	$scope.getTranslations = function() {
//		MessageService.getTranslations().then(function(response) {
//			$scope.translationMessages = response.data;
//		});
//	}
//	
//	var deferredTranslations = $scope.getTranslations();
//	
//	DashboardService.getProfiles().then(function(response) {
//		$scope.profiles = response.data.profiles;
//	});
	
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
	}
	
	$scope.removeInput = function(index) {
		$scope.websites.map(function(obj, i) {
			if (obj.i == index) {
				return $scope.websites.splice(i, 1);
			}
		});
	}
	
	$scope.isValidWebsite = function(website) {
		var isValidWebsite = website.url !== undefined && website.url.length !== 0;
		var element = document.querySelector('[name="website"][data-index="' + website.i + '"]');
		// FIXME: should learn a better way how to manipulate elements with Angular
		validateInput(isValidWebsite, element, $scope.translationMessages['new.profile.modal.url.validation.error']);
		return isValidWebsite ? '' : 'contains-errors';
	}
	
	$scope.collectData = function(isValid) {
		var params = {params: {website: []}};
		
		if (isValid) {
			$scope.dom.profileOptions.isShow = false;
			$scope.dom.newProfileContent.isShow = false;
			$scope.dom.newProfileSpinner.isShow = true;
			
			angular.forEach($scope.websites, function(website, i) {
				params.params.website.push(website.url);
			});
			
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
	
	// TODO: replace vanilla JS?
	function validateInput(isValid, element, text) {
		if (!isValid) {
			element.setAttribute('data-toggle', 'tooltip');
			element.setAttribute('title', text);
		} else {
			element.removeAttribute('data-toggle');
			element.removeAttribute('title');
		}
	}
}]);