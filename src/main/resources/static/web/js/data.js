const { createApp } = Vue

let app = createApp({
    
    data(){
        return{
            modal: true  
        }
    },
    

    methods : {
        mostrarModal(){
            this.modal= true
        },
        cerrarModal(){
            this.modal= false
        },
    

        
    }

})

app.mount("#app")