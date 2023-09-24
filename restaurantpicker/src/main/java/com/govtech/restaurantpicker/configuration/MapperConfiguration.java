package com.govtech.restaurantpicker.configuration;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.govtech.restaurantpicker.model.RestaurantChoice;
import com.govtech.restaurantpicker.model.RestaurantUser;
import com.govtech.restaurantpicker.model.vo.RestaurantChoiceVO;
import com.govtech.restaurantpicker.model.vo.RestaurantUserVO;

@org.springframework.context.annotation.Configuration
public class MapperConfiguration {

	@Autowired
	private ModelMapper modelMapper;

	public RestaurantUserVO mapRestaurantUserToRestaurantUserVO(RestaurantUser restaurantUser) {
		return modelMapper.map(restaurantUser, RestaurantUserVO.class);
	}

	public RestaurantChoiceVO mapRestaurantChoiceToRestaurantChoiceVO(RestaurantChoice restaurantChoice) {
		return modelMapper.map(restaurantChoice, RestaurantChoiceVO.class);
	}

	public List<RestaurantChoiceVO> mapRestaurantChoiceListToRestaurantChoiceVOList(
			List<RestaurantChoice> restaurantChoiceList) {
		return restaurantChoiceList.stream().map(vo -> modelMapper.map(vo, RestaurantChoiceVO.class))
				.collect(Collectors.toList());
	}

}
