package dgtic.modulo10.servers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Servidor {
    private String hostname;
    private String ipAddress;
    private String cloudProvider;
    private String OS;

    @Override
    public String toString() {
        return "Servidor {" +
                "hostname='" + hostname + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", cloudProvider='" + cloudProvider + '\'' +
                ", OS='" + OS + '\'' +
                '}';
    }
}
