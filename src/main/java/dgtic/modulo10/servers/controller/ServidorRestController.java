package dgtic.modulo10.servers.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dgtic.modulo10.servers.model.Servidor;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/servidores")
public class ServidorRestController {
    private static final String NOMBRE = "David Israel Gutiérrez Núñez";
    private static final String[] cloudProviders = new String[]{"AWS", "Azure", "GCP"};
    private static final String[] operatingSystems = new String[]{"Windows", "Linux"};

    private HashMap<Integer, Servidor> servidores = new HashMap<>();

    public ServidorRestController() {
        for (int i = 1; i <= 10; i++) {
            Servidor servidor = new Servidor();
            servidor.setHostname("host" + i);
            servidor.setIpAddress("192.168.0." + i);
            servidor.setCloudProvider(cloudProviders[i % 3]);
            servidor.setOS(operatingSystems[i % 2]);
            servidores.put(i, servidor);
        }
    }

    @GetMapping(path = "/creditos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCreditos() {
        return new ResponseEntity<>(NOMBRE, HttpStatus.OK);
    }

    @GetMapping(path =  "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<Integer, Servidor>> getServidores(@RequestParam String param) {
        return new ResponseEntity<>(servidores, HttpStatus.OK);
    }
    
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Servidor> getServidorById(@PathVariable int id) {
        Servidor servidor = servidores.get(id);
        if (servidor != null) return ResponseEntity.ok(servidor);
        else return ResponseEntity.notFound().build();
    }
    
    @PostMapping(path = "/", headers = {"Accept=application/json"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Servidor> createServidor(@RequestBody Servidor servidor) {
        int id = 1;
        while (servidores.containsKey(id)) id++;
        servidores.put(id, servidor);
        return new ResponseEntity<>(servidor, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", headers = {"Accept=application/json"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Servidor> updateServidor(@PathVariable int id, @RequestBody Servidor servidor) {
        if (servidores.containsKey(id)) {
            servidores.replace(id, servidor);
            return ResponseEntity.ok(servidores.get(id));
        }
        servidores.put(id, servidor);
        return new ResponseEntity<>(servidores.get(id), HttpStatus.CREATED);
    }
    
    @PatchMapping(path = "/{id}", headers = {"Accept=application/json"},produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Servidor> actualizarServidor(@PathVariable int id, @RequestBody Servidor servidor) {
        Servidor servidorRes = servidores.get(id);
        if (servidorRes == null) return ResponseEntity.notFound().build();
        if (servidor.getHostname() != null) servidorRes.setHostname(servidor.getHostname());
        if (servidor.getCloudProvider() != null) {
            int i;
            for (i = 0; i < cloudProviders.length; i++) {
                if (servidor.getCloudProvider().equals(cloudProviders[i])) {
                    servidorRes.setCloudProvider(servidor.getCloudProvider());
                    break;
                }
            }
            if (i == cloudProviders.length) return new ResponseEntity<>(servidor, HttpStatusCode.valueOf(422));
        }
        if (servidor.getIpAddress() != null) {
            String ipPattern =  "^([0-9]{1,3}\\.){3}[0-9]{1,3}$";
            if (servidor.getIpAddress().matches(ipPattern)) {
                String[] segmentos = servidor.getIpAddress().split("\\.");
                for (String segmento : segmentos) {
                    int num = Integer.parseInt(segmento);
                    if (num < 0 || num > 255) return new ResponseEntity<>(servidor, HttpStatusCode.valueOf(422)); 
                }
                servidorRes.setIpAddress(servidor.getIpAddress());
            }
            else return new ResponseEntity<>(servidor, HttpStatusCode.valueOf(422));
        }
        if (servidor.getOS() != null) {
            int i;
            for (i = 0; i < operatingSystems.length; i++) {
                if (servidor.getOS().equals(operatingSystems[i])) {
                    servidorRes.setOS(servidor.getOS());
                    break;
                }
            }
            if (i == cloudProviders.length) return new ResponseEntity<>(servidor, HttpStatusCode.valueOf(422));
        }
        return ResponseEntity.ok(servidorRes);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Servidor> deleteServidor(@PathVariable int id) {
        if (servidores.containsKey(id)) return new ResponseEntity<>(servidores.remove(id), HttpStatusCode.valueOf(204));
        return ResponseEntity.notFound().build();
    }
    
}
