import React from 'react';
import { Text, View, FlatList } from 'react-native';

export default class Lista extends React.Component {
  constructor(props) {
    super(props);
    this.state = { 
      movies: [],
      isLoading: true
     };
  }
  
  componentDidMount() {
    fetch('http://192.168.43.76:8080/movie')
      .then((response) => response.json())
      .then((responseJson) => {
      this.setState({
        isLoading: false,
        movies: responseJson,
      });

      console.log('gataa');
      console.log(this.state.movies);
    })
    .catch((error) =>{
      console.error(error);
    });
  }

    render() {
      return (
          <View>
              <Text>Home111</Text>
              {Array.isArray(this.state.movies) &&
              (
              <FlatList
                  data={this.state.movies}
                  renderItem={({item}) => <Text>{item.details}</Text>}
              />
              )}
          </View>
      )
}
}