const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
          type : "",
          color : ""
        }
    },
    created(){
       
    },

    methods : {
       createCard(){
        axios.post("/api/clients/current/cards?cardType=" + this.type + "&color=" + this.color )
        .then(response =>
            console.log(response))
        .catch(error => 
            console.log(error))
       }
    }

})

app.mount("#app")