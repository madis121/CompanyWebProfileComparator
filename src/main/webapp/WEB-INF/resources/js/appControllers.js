var module = angular.module('cwpc.controllers', []);

module.controller('DashboardController', ['$scope', '$timeout', 'CONSTANTS', 'DashboardService', 'MessageService', function($scope, $timeout, CONSTANTS, DashboardService, MessageService) {
	$scope.dom = {
		profileOptions: {isShow: true},
		newProfileContent: {isShow: true},
		newProfileSpinner: {isShow: false},
		newProfileResult: {isShow: false},
		newProfileClean: {isShow: false},
		newProfileButtons: {isShow: false}
	};
	
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
	
	$scope.editProfile = {};
	$scope.deleteProfileId;
	
	getTranslations();
	getProfiles();
	
	function getTranslations() {
		MessageService.getTranslations().then(function success(response) {
			$scope.translationMessages = response.data;
		}, function error(response) {
			console.log(response);
		});
	}
	
	function getProfiles() {
		DashboardService.getProfiles().then(function success(response) {
			$scope.profiles = response.data.profiles;
		}, function error(response) {
			$scope.profiles = [];
			console.log(response);
		});
	}
	
	// TODO: replace jQuery?
 	$scope.openNewProfileModal = function() {
		$('#newProfileModal').modal('show');
	}
 	
 	$scope.saveProfile = function() {
 		var profile;
 		var isValid = false;
 		
 		if ($scope.profileOption == 1) {
 			profile = $scope.generatedProfile;
 			isValid = $scope.createProfile.$valid;
 		} else if ($scope.profileOption == 2) {
 			profile = $scope.cleanProfile;
 			isValid = $scope.createProfileClean.$valid;
 		}
 		
 		if (isValid) {
 			var params = {name: profile.name, keywords: profile.keywords, urls: profile.urls};
 			DashboardService.createProfile(params).then(function success(response) {
 				getProfiles();
 				$('#newProfileModal').modal('hide');
 			}, function error(response) {
 				console.log(response);
 			});
 		}
 	}
	
	$scope.openEditProfileModal = function(profileId) {
		var params = {id: profileId};
		// TODO: replace jQuery?
		$('[name="updateProfileForm"] [name="keywords"]').tagsinput('removeAll');
		
		DashboardService.getProfile(params).then(function success(response) {
			$scope.editProfile = response.data.profile;
			angular.forEach($scope.editProfile.keywords.split(','), function(keyword, i) {
				// TODO: replace jQuery?
				$('[name="updateProfileForm"] [name="keywords"]').tagsinput('add', keyword);
			});
			$('#profileEditModal').modal('show');
		}, function error(response) {
			showErrorNotification($scope.translationMessages['notification.common.error']);
		});
	}
	
	$scope.isProfileKeywordsValid = function(keywords) {
		if (keywords) {
			var isProfileKeywordsValid = keywords.split(',').length >= 5;
			$scope.updateProfileForm.$valid = isProfileKeywordsValid === true ? true : false;
			var element = document.querySelector('[name="updateProfileForm"] .keywords');
			// FIXME: should learn a better way how to manipulate elements with Angular
			validateKeywordsInput(isProfileKeywordsValid, element, $scope.translationMessages['new.profile.modal.error.must.contain.keywords']);
		} else {
			$scope.updateProfileForm.$valid = false;
		}
	}
	
	$scope.editProfile = function(isValid) {
		if (isValid) {
			var params = {id: $scope.editProfile.id, name: $scope.editProfile.name, keywords: $scope.editProfile.keywords};
			DashboardService.updateProfile(params).then(function success(response) {
				// TODO: replace jQuery?
				$('#profileEditModal').modal('hide');
			}, function error(response) {
				console.log(response);
			});
		}
	}
	
	// TODO: replace jQuery?
	$scope.openDeleteProfileModal = function(profileId) {
		$scope.deleteProfileId = profileId;
	    $('#profileDeleteModal').modal('show');
	}
	
	$scope.chooseProfileCreation = function() {
		if ($scope.profileOption == 1) {
			$scope.dom.newProfileClean.isShow = false;
			// TODO: replace jQuery?
			if ($('[name="createProfile"] [name="keywords"]').tagsinput('items').length > 0) {
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
			
			DashboardService.collectData(params).then(function success(response) {
				var collectedData = response.data.collectedData;
				angular.forEach(collectedData.keywords, function(keyword, i) {
					// TODO: replace jQuery?
					$('[name="createProfile"] [name="keywords"]').tagsinput('add', keyword.word);
				});
				$scope.generatedProfile.urls = collectedData.websites.toString();
				$scope.dom.newProfileSpinner.isShow = false;
				$scope.dom.profileOptions.isShow = true;
				$scope.dom.newProfileResult.isShow = true;
				$scope.dom.newProfileButtons.isShow = true;
			}, function error(response) {
				console.log(response);
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
			$('[name="createProfile"] [name="keywords"]').tagsinput('removeAll');
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
			$('[name="createProfileClean"] [name="keywords"]').tagsinput('removeAll');
		}
	}
	
	$scope.deleteProfile = function(profileId) {
		var params = {id: profileId};
		
		DashboardService.deleteProfile(params).then(function success(response) {
			$scope.profiles.map(function(profile, i) {
				if (profile.id == profileId) {
					return $scope.profiles.splice(i, 1);
				}
			});
			$scope.deleteProfileId = undefined;
			$('#profileDeleteModal').modal('hide');
		}, function error(response) {
			console.log(response);
		});
	}
	
	// TODO: replace vanilla JS?
	function validateInput(isValid, element, text) {
		if (!isValid) {
			element.setAttribute('data-toggle', 'tooltip');
			element.setAttribute('data-original-title', text);
			initializeTooltips();
		} else {
			element.removeAttribute('data-toggle');
			element.removeAttribute('data-original-title');
		}
	}
	
	function validateKeywordsInput(isValid, element, text) {
		if (!isValid) {
			element.setAttribute('data-toggle', 'tooltip');
			element.setAttribute('data-original-title', text);
			element.classList.add('contains-errors');
			initializeTooltips();
		} else {
			element.removeAttribute('data-toggle');
			element.removeAttribute('data-original-title');
			element.classList.remove('contains-errors');
		}
	}
	
	// TODO: replace jQuery?
	function initializeTooltips() {
		$("[data-toggle=tooltip").tooltip();
		$('.bootstrap-tagsinput').addClass('col-md-10 keywords');
	}
	
	function initializeInputTags() {
		$('input[data-role="tagsinput"]').tagsinput('items');
	}
	
	function showErrorNotification(text) {
		$.notify({ message: '<i class="fas fa-exclamation-triangle"></i>' + text }, { type: 'danger' });
	}
	
 	function displayNotifications() {
//		if ('${action}' == PROFILE_SAVED && '${not hasErrors}') {
//			$.notify('<i class="fas fa-check"></i> <spring:message code="notification.profile.saved" />');
//		}
//		if ('${action}' == PROFILE_UPDATED && '${not hasErrors}') {
//			$.notify('<i class="fas fa-check"></i> <spring:message code="notification.profile.updated" />');
//		}
//		if ('${action}' == PROFILE_DELETED && '${not hasErrors}') {
//			$.notify('<i class="fas fa-check"></i> <spring:message code="notification.profile.deleted" />');
//		}
	}
}]);