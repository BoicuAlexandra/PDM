var server = 'http://192.168.43.76:8080';

export async function getRequest(path, dispatch) {
    fetch(server + path)
    .then((response) => response.json())
    .then((responseJson) => {
       dispatch(responseJson);
    })
    .catch((error) =>{
        console.error(error);
    });
}