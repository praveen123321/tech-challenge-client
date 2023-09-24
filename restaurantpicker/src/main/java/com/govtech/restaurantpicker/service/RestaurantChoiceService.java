package com.govtech.restaurantpicker.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.govtech.restaurantpicker.configuration.MapperConfiguration;
import com.govtech.restaurantpicker.model.RestaurantChoice;
import com.govtech.restaurantpicker.model.RestaurantUser;
import com.govtech.restaurantpicker.model.vo.RestaurantChoiceVO;
import com.govtech.restaurantpicker.repository.RestaurantChoiceRepository;

@Service
public class RestaurantChoiceService {

	private final RestaurantChoiceRepository restaurantChoiceRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private MapperConfiguration mapper;

	@Autowired
	public RestaurantChoiceService(RestaurantChoiceRepository restaurantChoiceRepository) {
		this.restaurantChoiceRepository = restaurantChoiceRepository;
	}

	public RestaurantChoiceVO saveChoice(RestaurantChoice restaurantChoice) throws Exception {
		RestaurantUser restaurantUser = userService.getUserByUsername(restaurantChoice.getUser().getUsername());
		restaurantChoice.setUser(restaurantUser);
		RestaurantChoice resChoice = restaurantChoiceRepository.save(restaurantChoice);

		RestaurantChoiceVO restaurantChoiceVO = mapper.mapRestaurantChoiceToRestaurantChoiceVO(resChoice);
		return restaurantChoiceVO;
	}



	public RestaurantChoiceVO getRandomPick(String username) {
		List<RestaurantChoiceVO> choiceListById = getAllChoicesByUserId(username);
		RestaurantChoiceVO restaurantChoiceVO = null;
		if (choiceListById != null && choiceListById.size() > 0) {
			Random random = new Random();
			int randomIndex = random.nextInt(choiceListById.size());
			restaurantChoiceVO = choiceListById.get(randomIndex);

		}
		return restaurantChoiceVO;
	}

	public List<RestaurantChoiceVO> getAllChoicesByUserId(String username) {
		RestaurantUser restaurantUser = userService.getUserByUsername(username);
		return mapper.mapRestaurantChoiceListToRestaurantChoiceVOList(
				restaurantChoiceRepository.findAllByUserId(restaurantUser.getUserId()));
	}
}
