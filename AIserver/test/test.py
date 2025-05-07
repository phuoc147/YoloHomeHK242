
import subprocess
import re
from collections import defaultdict
import os

def run_wmic_field(field: str) -> str:
    try:
        command = f'wmic diskdrive get {field}'
        result = subprocess.check_output(command, shell=True).decode().strip().split('\n')[1:]
        for line in result:
            value = line.strip()
            if value:
                return value
    except Exception as e:
        print(f"Failed to get {field}: {e}")
    return ""
def get_size(size: int) -> str:
    """Convert size in bytes to a human-readable format."""
    for unit in ['B', 'KB', 'MB', 'GB', 'TB']:
        if size < 1024:
            return f"{size:.2f} {unit}"
        size /= 1024
    return f"{size:.2f} PB"
def get_physical_disk_info():
        hard_drives = []

        try:
            startupinfo = subprocess.STARTUPINFO()
            startupinfo.dwFlags |= subprocess.STARTF_USESHOWWINDOW

            # Lấy thông tin ổ đĩa
            disk_command = "wmic diskdrive get model,size,deviceid,index,InterfaceType,Status,FirmwareRevision,mediaType,serialNumber,partitions"
            try:
                disk_result = run_wmic_field("model,size,deviceid,index,InterfaceType,Status,FirmwareRevision,mediaType,serialNumber,partitions")
                
            except subprocess.SubprocessError as e:
                # self.Error.update({"Disk":str(e)})
                print(f"Error executing disk command: {e}")
                return hard_drives

            # Lấy thông tin phân vùng
            partition_command = "wmic partition get deviceid,diskindex,size"
            try:
                partition_result = subprocess.check_output(partition_command, startupinfo=startupinfo, shell=True).decode().strip()
            except subprocess.SubprocessError as e:
                print(f"Error executing partition command: {e}")
                # self.Error.update({"partition":str(e)})
                return hard_drives

            # Parse kết quả
            disk_lines = disk_result.split('\n')[1:]
            partition_lines = partition_result.split('\n')[1:]

            # Nhóm phân vùng theo disk index
            partitions_by_disk = defaultdict(list)
            try:
                for partition in partition_lines:
                    parts = re.split(r'\s{2,}', partition.strip())
                    if len(parts) == 3:
                        partition_deviceid, partition_diskindex, partition_size = parts
                        partitions_by_disk[partition_diskindex].append({
                            "index": len(partitions_by_disk[partition_diskindex]),
                            "size": get_size(int(partition_size))
                        })
            except (ValueError, IndexError) as e:
                # self.Error.update({"partition":str(e)})
                print(f"Error parsing partition info: {e}")
                return hard_drives

            # Xử lý từng ổ đĩa và thêm các phân vùng tương ứng
            for disk in disk_lines:
                try:
                    parts = re.split(r'\s{2,}', disk.strip())
                    if len(parts) >= 10:
                        print(parts)
                        deviceid, firmware, disk_index, interface_type, media_type, model, partitions_num, serial_number, size, status = parts

                        hard_drives.append({
                            "deviceid": deviceid[4:],
                            "firmware": firmware,
                            "index": disk_index,
                            "interface_type": interface_type,
                            "mediaType": media_type,
                            "model": model,
                            "NumberOfPartitions": int(partitions_num),
                            "serialNumber": serial_number,
                            "size": get_size(int(size)),
                            "diskStatus": status,
                            "partitions": partitions_by_disk[disk_index],
                            "name":f"{model} {interface_type}"
                        })
                except (ValueError, IndexError) as e:
                    # self.Error.update({"disk":str(e)})
                    print(f"Error parsing disk info: {e}")
                    return None
        except Exception as e:
            # self.Error.update({"disk":str(e)})
            print(f"Error executing command: {e}")
            return hard_drives
        return hard_drives


print(get_physical_disk_info())